package edu.mirea.myinvest.controller;


import edu.mirea.myinvest.domain.dto.pagination.PageResponse;
import edu.mirea.myinvest.domain.dto.security.SecurityFilter;
import edu.mirea.myinvest.service.SecurityService;
import edu.mirea.myinvest.domain.dto.security.HistoryDataResponse;
import edu.mirea.myinvest.domain.dto.security.SecurityResponse;
import edu.mirea.myinvest.mapper.SecurityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/securities")
@RequiredArgsConstructor
@Tag(name = "Работа с ценными бумагами")
public class SecurityController {

    private final SecurityService service;

    private final SecurityMapper mapper;

    @Operation(summary = "Получение постов по фильтру")
    @PostMapping("/filter")
    public PageResponse<SecurityResponse> findPostsByFilter(@RequestBody @Valid SecurityFilter filter) {
        var result = new PageResponse<SecurityResponse>();
        var securities = service.findByFilter(filter);
        result.setTotalPages(securities.getTotalPages());
        result.setTotalSize(securities.getTotalElements());
        result.setPageNumber(securities.getNumber());
        result.setPageSize(securities.getSize());
        result.setContent(mapper.toResponse(securities.getContent()));
        return result;
    }

    @Operation(summary = "Получение постов по фильтру")
    @GetMapping("/history/{secId}")
    public HistoryDataResponse getHistory(@PathVariable Long secId) {
        return service.getHistory(secId);
    }


    @Operation(summary = "Добавление поста в избранное")
    @PostMapping("/subscribe/{secId}")
    public void subscribeTo(@PathVariable Long secId) {
        service.subscribeTo(secId);
    }

    @Operation(summary = "Добавление поста в избранное")
    @PostMapping("/unsubscribe/{secId}")
    public void unsubscribeFrom(@PathVariable Long secId) {
        service.unsubscribeFrom(secId);
    }

}
