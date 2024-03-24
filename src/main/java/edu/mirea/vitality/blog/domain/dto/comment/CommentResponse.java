package edu.mirea.vitality.blog.domain.dto.comment;

import edu.mirea.vitality.blog.domain.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;


@Schema(description = "Ответ на запрос комментария")
public record CommentResponse(
        @Schema(description = "Id комментария", example = "1")
        Long id,

        @Schema(description = "Текст комментария", example = "Комментарий")
        String content,

        @Schema(description = "Id связанного поста", example = "1")
        Long postId,

        @Schema(description = "Время загрузки файла", example = "2023-10-10T10:10:10.000+00:00")
        OffsetDateTime createdAt,

        @Schema(description = "Время обновления файла", example = "2024-10-10T10:10:10.000+00:00")
        OffsetDateTime updatedAt,

        @Schema(description = "Автора комментария")
        UserResponse author
) {
}
