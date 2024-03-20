package edu.mirea.myinvest.domain.dto.category;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос категории")
public record CategoryResponse(

        @Schema(description = "Id категории")
        Long id,
        @Schema(description = "Название категории")
        String title

) {
}
