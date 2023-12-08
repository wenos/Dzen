package edu.mirea.myinvest.domain.dto.user;

import edu.mirea.myinvest.validation.constraints.ValidRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на обновление роли пользователя")
public record UpdateUserRoleRequest(

        @Schema(description = "Идентификатор пользователя", example = "1")
        Long id,

        @Schema(description = "Роль пользователя", example = "ROLE_USER")
        @ValidRole(message = "Значение роли недопустимо")
        @NotBlank(message = "Роль является обязательным параметром")
        String role
) {
}