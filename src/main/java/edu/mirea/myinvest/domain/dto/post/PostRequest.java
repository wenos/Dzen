package edu.mirea.myinvest.domain.dto.post;

import edu.mirea.myinvest.validation.constraints.ValidFiles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Schema(description = "Запрос на создание поста")
public record PostRequest(
        @Schema(description = "Заголовок поста", example = "Коллоквиум по Java по асинхронному программированию")
        @Size(min = 5, max = 255, message = "Заголовок должен быть от 5 до 255 символов")
        @NotBlank(message = "Заголовок не должен быть пустым")
        String title,

        @Schema(description = "Содержание поста", example = "Коллоквиум по Java по асинхронному программированию состоится 20.05.2021 в 10:00")
        @Size(max = 9_999, message = "Содержание должно быть от 0 до 9999 символов")
        String content,

        @Schema(description = "Проверка на новость", example = "true")
        @NotNull(message = "Поле является обязательным")
        Boolean isNews,

        @Schema(description = "Файлы")
        @ValidFiles
        List<MultipartFile> attachments
) {
}