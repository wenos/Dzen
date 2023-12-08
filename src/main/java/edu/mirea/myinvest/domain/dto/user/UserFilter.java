package edu.mirea.myinvest.domain.dto.user;

import edu.mirea.myinvest.domain.dto.pagination.PageRequestDTO;
import edu.mirea.myinvest.validation.constraints.ValidRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос пользователей по фильтру")
public class UserFilter extends PageRequestDTO {

    /**
     * Идентификатор пользователя
     */
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    /**
     * Имя пользователя
     */
    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(max = 50, message = "Имя пользователя должно содержать до 50 символов")
    private String username;

    /**
     * Почта
     */
    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(max = 255, message = "Адрес электронной почты должен до 255 символов")
    private String email;

    /**
     * Чекер бана
     */
    @Schema(description = "Чекер бана", example = "true")
    private Boolean isBanned;

    /**
     * Роль пользователя
     */
    @Schema(description = "Роль пользователя", example = "ROLE_USER")
    @ValidRole(nullable = true, message = "Значение роли недопустимо")
    private String role;
}
