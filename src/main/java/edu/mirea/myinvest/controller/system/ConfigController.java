package edu.mirea.myinvest.controller.system;

import edu.mirea.myinvest.domain.dto.system.ConfigUnitResponse;
import edu.mirea.myinvest.domain.dto.system.UpdateConfigUnitRequest;
import edu.mirea.myinvest.domain.dto.system.UpdateJobIntervalRequest;
import edu.mirea.myinvest.domain.model.system.SystemPropertyKey;
import edu.mirea.myinvest.mapper.ConfigMapper;
import edu.mirea.myinvest.service.sheduled.JobSchedulingService;
import edu.mirea.myinvest.service.system.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
@Tag(name = "Работа с конфигурацией")
public class ConfigController {
    private final ConfigService service;
    private final ConfigMapper mapper;
    private final JobSchedulingService jobSchedulingService;


    /**
     * Получение всех конфигураций
     *
     * @return список конфигураций
     */
    @Operation(summary = "Получение всех конфигураций")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ConfigUnitResponse> getAll() {
        return mapper.toResponse(service.getProperties());
    }

    /**
     * Обновление конфигурации
     *
     * @param request запрос на обновление конфигурации
     */
    @Operation(summary = "Обновление конфигурации")
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateConfig(@RequestBody @Valid UpdateConfigUnitRequest request) {
        service.updateUnit(SystemPropertyKey.valueOf(request.key()), request.value());
    }

    /**
     * Изменение интервала удаления комментариев
     *
     * @param request запрос на изменение интервала
     * @param job     джоба для изменения
     */
    @Operation(summary = "Изменение интервала удаления комментариев")
    @PutMapping("/interval/{job}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateInterval(
            @RequestBody @Valid UpdateJobIntervalRequest request,
            @PathVariable String job
    ) {
        jobSchedulingService.updateInterval(request, job);
    }
}
