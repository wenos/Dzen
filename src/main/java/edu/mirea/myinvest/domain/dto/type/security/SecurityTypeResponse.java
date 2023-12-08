package edu.mirea.myinvest.domain.dto.type.security;

import io.swagger.v3.oas.annotations.media.Schema;
import org.mapstruct.Mapper;

import java.time.OffsetDateTime;

public record SecurityTypeResponse (
    @Schema(description = "Идентификатор типа ценной бумаги", example = "1")
    Long id,

    @Schema(description = "Название типа", example = "bond")
    String name,

    @Schema(description = "Заголовок типа", example = "Облигации")
    String title
) {

}
