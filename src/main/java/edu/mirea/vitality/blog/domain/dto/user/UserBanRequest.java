package edu.mirea.vitality.blog.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Builder;

@Builder
@Schema(description = "Запрос на бан пользователя")
public record UserBanRequest(
        @Schema(description = "Идентификатор пользователя", example = "1")
        Long id,

        @Schema(description = "Дни до конца бана", example = "10")
        @Max(value = 365000, message = "Максимальное количество дней не должно быть больше 365000")
        Integer days,

        @Schema(description = "Часы до конца бана", example = "10")
        @Max(value = 23, message = "Максимальное количество часов не должно быть больше 23")
        Integer hours,

        @Schema(description = "Минуты до конца бана", example = "10")
        @Max(value = 59, message = "Максимальное количество минут не должно быть больше 59")
        Integer minutes
) {
}