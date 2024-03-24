package edu.mirea.vitality.blog.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на удаление пользователя")
public record DeleteUserRequest(
        @Schema(description = "Идентификатор пользователя", example = "1")
        long userId,

        @Schema(description = "Удаляются ли посты пользователя")
        boolean deletePosts,

        @Schema(description = "Удаляются ли комментарии пользователя")
        boolean deleteComments
) {
}