package edu.mirea.myinvest.domain.dto.system;

import edu.mirea.myinvest.validation.constraints.ValidSystemProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на обновление конфигурации")
@ValidSystemProperty(message = "Неверный ключ конфигурации")
public record UpdateConfigUnitRequest(
        @Schema(description = "Значение конфигурации")
        @NotBlank(message = "Значение конфигурации не может быть пустым")
        String value,

        @Schema(description = "Ключ конфигурации")
        @NotBlank(message = "Ключ конфигурации не может быть пустым")
        @Size(max = 255, message = "Ключ конфигурации не может быть длиннее 255 символов")
        String key
) {
}