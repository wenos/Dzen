package edu.mirea.myinvest.controller;


import edu.mirea.myinvest.domain.dto.type.security.SecurityTypeResponse;
import edu.mirea.myinvest.mapper.SecurityTypeMapper;
import edu.mirea.myinvest.service.SecurityTypeService;
import edu.mirea.myinvest.domain.model.SecurityType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/security-types")
@RequiredArgsConstructor
@Tag(name = "Работа с типами ценных бумаг")
public class SecurityTypeController {

    private final SecurityTypeService service;
    private final SecurityTypeMapper mapper;

    @Operation(description = "Получение всех типо ценных бумаг")
    @GetMapping
    public List<SecurityTypeResponse> getAll() {
        List<SecurityType> types = service.getAll();
        return mapper.toResponse(types);
    }

}
