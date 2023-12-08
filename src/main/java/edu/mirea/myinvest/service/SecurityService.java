package edu.mirea.myinvest.service;

import edu.mirea.myinvest.domain.dto.security.SecurityFilter;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.exception.security.UserAlreadySubscribeProblem;
import edu.mirea.myinvest.exception.security.UserSecurityRelationNotFoundProblem;
import edu.mirea.myinvest.repository.UserSecurityRelRepository;
import edu.mirea.myinvest.domain.dto.security.HistoryDataResponse;
import edu.mirea.myinvest.domain.dto.security.HistoryLineResponse;
import edu.mirea.myinvest.domain.model.Security;
import edu.mirea.myinvest.domain.model.UserSecurityRel;
import edu.mirea.myinvest.exception.security.SecurityNotFoundProblem;
import edu.mirea.myinvest.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final UserSecurityRelRepository userSecurityRelRepository;
    private final UserService userService;

    public Page<Security> findByFilter(SecurityFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return securityRepository.findAllWithFilter(filter.getName(),
                filter.getShortname(),
                filter.getSecId(),
                filter.getPrimaryBoardId(),
                filter.getTypeId(),
                filter.getUserId(),
                pageable);
    }

    public Optional<Security> findById(Long id) {
        return securityRepository.findById(id);
    }

    public Security getById(Long id) {
        return findById(id).orElseThrow(() -> new SecurityNotFoundProblem(id));
    }


    public HistoryDataResponse getHistory(Long id) {
        Security security = getById(id);
        String engine, market, board, secId;
        LocalDate firstDay = LocalDate.now().minusDays(30);
        LocalDate lastDay = LocalDate.now();
        secId = security.getSecId();
        board = security.getPrimaryBoardId();
        String typeName = security.getType().getName();
        if (typeName.contains("share")) {
            engine = "stock";
            market = "shares";
        } else if (typeName.contains("bond")) {
            engine = "stock";
            market = "bonds";
        } else {
            engine = "currency";
            market = "selt";
        }

        return HistoryDataResponse.builder()
                .primaryBoardId(board)
                .secId(secId)
                .shortname(security.getShortname())
                .id(security.getId())
                .name(security.getName())
                .data(getData(engine, market, board, secId, firstDay, lastDay)).build();
    }

    public List<HistoryLineResponse> getData(String engine, String market, String board, String secId, LocalDate firstDay,
                                             LocalDate lastDay) {

        String url = String.format("https://iss.moex.com/iss/history/engines/%s/markets/%s/boards/%s/securities/%s.json?from=%s&till=%s&iss.meta=off",
                engine, market, board, secId, firstDay, lastDay);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        List<HistoryLineResponse> lines = new ArrayList<>();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode == 200) {
                // Обработка ответа
                JSONObject jsonResponse = new JSONObject(responseBody);

                // Получение поля history из JSON
                JSONArray historyArray = jsonResponse.getJSONObject("history").getJSONArray("data");
                var a = jsonResponse.getJSONObject("history").getJSONArray("columns");
                Map<String, Integer> map = new HashMap<>();
                for (int i = 0; i < a.length(); ++i) {
                    map.put(a.getString(i), i);
                }

                for (int i = 0; i < historyArray.length(); i++) {
                    JSONArray item = historyArray.getJSONArray(i);

                    lines.add(HistoryLineResponse.builder()
                            .date(LocalDate.parse(item.getString(map.get("TRADEDATE"))))
                            .open(item.getDouble(map.get("OPEN")))
                            .low(item.getDouble(map.get("LOW")))
                            .high(item.getDouble(map.get("HIGH")))
                            .close(item.getDouble(map.get("CLOSE")))
                            .build());

                }

            } else {
                System.out.println("Ошибка при получении данных. Код ошибки: " + statusCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return lines;
    }


    public void subscribeTo(Long secId) {
        User user = userService.getCurrentUser();
        if (userSecurityRelRepository.existsByUserIdAndSecurityId(user.getId(), secId)) {
            throw new UserAlreadySubscribeProblem(user.getId());
        }
        Security security = getById(secId);
        UserSecurityRel rel = UserSecurityRel.builder()
                .security(security)
                .user(user)
                .build();
        userSecurityRelRepository.save(rel);

    }

    @Transactional
    public void unsubscribeFrom(Long secId) {
        User user = userService.getCurrentUser();
        if (!userSecurityRelRepository.existsByUserIdAndSecurityId(user.getId(), secId)) {
            throw new UserSecurityRelationNotFoundProblem(user.getId(), secId);
        }
        userSecurityRelRepository.deleteByUserIdAndSecurityId(user.getId(), secId);
    }


}
