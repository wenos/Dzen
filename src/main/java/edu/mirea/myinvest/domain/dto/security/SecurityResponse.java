package edu.mirea.myinvest.domain.dto.security;


import edu.mirea.myinvest.domain.dto.type.security.SecurityTypeResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос ценных бумаг по фильтру")
public record SecurityResponse(

        @Schema(description = "Идентификатор")
        Long id,

        @Schema(description = "Название")
        String name,

        @Schema(description = "Короткое название")
        String shortname,

        @Schema(description = "Id бумаги на бирже")
        String secId,

        @Schema(description = "Основное место торгов")
        String primaryBoardId,

        @Schema(description = "Тип бумаги")
        SecurityTypeResponse type

) {
}
