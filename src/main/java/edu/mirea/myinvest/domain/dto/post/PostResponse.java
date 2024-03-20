package edu.mirea.myinvest.domain.dto.post;

import edu.mirea.myinvest.domain.dto.category.CategoryResponse;
import edu.mirea.myinvest.domain.dto.user.UserResponse;
import edu.mirea.myinvest.domain.dto.file.UploadedFileResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "Ответ на запрос поста")
public record PostResponse(
        @Schema(description = "Идентификатор поста", example = "1")
        Long id,

        @Schema(description = "Заголовок поста", example = "Коллоквиум по Java по асинхронному программированию")
        String title,

        @Schema(description = "Содержание поста", example = "Коллоквиум по Java по асинхронному программированию состоится 20.05.2021 в 10:00")
        String content,

        @Schema(description = "Автора поста")
        UserResponse author,

        @Schema(description = "Привязанные файлы")
        List<UploadedFileResponse> attachments,

        @Schema(description = "Время загрузки файла", example = "2023-10-10T10:10:10.000+00:00")
        OffsetDateTime createdAt,

        @Schema(description = "Время обновления файла", example = "2024-10-10T10:10:10.000+00:00")
        OffsetDateTime updatedAt,

        @Schema(description = "Категория поста")
        CategoryResponse category,

        @Schema(description = "Количество лайков")
        Long likes
) {
}