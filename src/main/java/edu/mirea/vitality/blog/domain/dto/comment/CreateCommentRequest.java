package edu.mirea.vitality.blog.domain.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(description = "Запрос на создание комментария")
public record CreateCommentRequest(
        @Schema(description = "Текст комментария", example = "Комментарий")
        @NotBlank(message = "Текст комментария не должен быть пустым")
        @Size(min = 1, max = 999, message = "Текст комментария должен быть от 1 до 999 символов")
        String content,

        @Schema(description = "Id связанного поста", example = "1")
        @NotNull(message = "Id поста не должен быть пустым")
        Long postId,

        @Schema(description = "Анонимный ли комментарий", example = "false")
        boolean isAnonymous
) {
}
