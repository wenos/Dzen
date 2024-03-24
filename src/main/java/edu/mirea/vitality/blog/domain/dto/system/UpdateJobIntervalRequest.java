package edu.mirea.vitality.blog.domain.dto.system;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "Запрос на обновление задачи")
public record UpdateJobIntervalRequest(

        @Schema(description = "Дни до следующего запуска", example = "10")
        @Max(value = 3650, message = "Количество дней не должно превышать 3650")
        @Min(value = 0, message = "Количество дней не может быть отрицательным")
        Integer days,

        @Schema(description = "Часы до следующего запуска", example = "10")
        @Max(value = 23, message = "Количество часов не должно превышать 23")
        @Min(value = 0, message = "Количество часов не может быть отрицательным")
        Integer hours,

        @Schema(description = "Минуты до следующего запуска", example = "10")
        @Max(value = 59, message = "Количество минут не должно превышать 59")
        @Min(value = 0, message = "Количество минут не может быть отрицательным")
        Integer minutes,

        @Schema(description = "Секунды до следующего запуска", example = "10")
        @Max(value = 59, message = "Количество секунд не должно превышать 59")
        @Min(value = 0, message = "Количество секунд не может быть отрицательным")
        Integer seconds
) {
}