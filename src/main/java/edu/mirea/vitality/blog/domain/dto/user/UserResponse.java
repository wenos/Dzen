package edu.mirea.vitality.blog.domain.dto.user;

import edu.mirea.vitality.blog.domain.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Set;

@Builder
@Schema(description = "Ответ на запрос пользователя")
public record UserResponse(

        @Schema(description = "Идентификатор пользователя", example = "1")
        Long id,

        @Schema(description = "Логин пользователя", example = "ivanov")
        String username,


        @Schema(description = "Почта пользователя", example = "example@tinkoff.ru")
        String email,


        @Schema(description = "Дата окончания бана", example = "2021-10-10T10:10:10+03:00")
        OffsetDateTime bannedAt,

        @Schema(description = "Роль пользователя", example = "ROLE_USER")
        Role role,

        @Schema(description = "Дата удаления пользователя", example = "2021-10-10T10:10:10+03:00")
        OffsetDateTime deletedAt,

        @Schema(description = "Список categoryId подписок")
        Set<Long> subscribes
) {
}