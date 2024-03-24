package edu.mirea.vitality.blog.domain.dto.system;

import edu.mirea.vitality.blog.domain.model.system.ConfigFieldType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос конфигурации")
public record ConfigUnitResponse(
        @Schema(description = "Ключ конфигурации")
        String key,

        @Schema(description = "Значение конфигурации")
        String value,

        @Schema(description = "Тип поля")
        ConfigFieldType type,

        @Schema(description = "Кастомный обработчик")
        Boolean customHandler
) {
}
