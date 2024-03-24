package edu.mirea.vitality.blog.domain.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Schema(description = "Запрос на создание категории")
public record CategoryRequest(

        @NotBlank(message = "Название категории не может быть пустым")
        String title
) {
}
