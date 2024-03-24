package edu.mirea.vitality.blog.controller;

import edu.mirea.vitality.blog.domain.dto.pagination.PageResponse;
import edu.mirea.vitality.blog.mapper.UserMapper;
import edu.mirea.vitality.blog.service.UserService;
import edu.mirea.vitality.blog.domain.dto.user.DeleteUserRequest;
import edu.mirea.vitality.blog.domain.dto.user.UpdateUserEmailRequest;
import edu.mirea.vitality.blog.domain.dto.user.UpdateUserInfoRequest;
import edu.mirea.vitality.blog.domain.dto.user.UpdateUserPasswordRequest;
import edu.mirea.vitality.blog.domain.dto.user.UpdateUserRoleRequest;
import edu.mirea.vitality.blog.domain.dto.user.UpdateUserUsernameRequest;
import edu.mirea.vitality.blog.domain.dto.user.UserBanRequest;
import edu.mirea.vitality.blog.domain.dto.user.UserFilter;
import edu.mirea.vitality.blog.domain.dto.user.UserProfileResponse;
import edu.mirea.vitality.blog.domain.dto.user.UserResponse;
import edu.mirea.vitality.blog.domain.model.User;
import edu.mirea.vitality.blog.service.DeleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @class UserController
 * @brief Контроллер для работы с пользователями.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Работа с пользователями")
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final DeleteService deleteService;
    private final UserMapper mapper;

    /**
     * @brief Обновляет имя пользователя.
     * @param request Запрос на обновление имени пользователя.
     */
    @Operation(summary = "Обновление имени пользователя")
    @PostMapping("/change-username") // TODO: Можно переписать на patch - логически подходит больше
    public void selfUpdateUsername(@RequestBody @Valid UpdateUserUsernameRequest request) {
        service.selfUpdateUsername(request);
    }

    /**
     * @brief Обновляет почту пользователя.
     * @param request Запрос на обновление почты пользователя.
     */
    @Operation(summary = "Обновление почты пользователя")
    @PostMapping("/change-email")
    public void selfUpdateEmail(@RequestBody @Valid UpdateUserEmailRequest request) {
        service.selfUpdateEmail(request);
    }

    /**
     * @brief Обновляет информацию о пользователе.
     * @param request Запрос на обновление информации о пользователе.
     */
    @Operation(summary = "Обновление информации о пользователи")
    @PostMapping("/change-info")
    public void selfUpdateInfo(@RequestBody @Valid UpdateUserInfoRequest request) {
        service.selfUpdateInfo(request);
    }

    /**
     * @brief Получает данные профиля пользователя.
     * @param username Имя пользователя.
     * @return Ответ с данными профиля пользователя.
     */
    @Operation(summary = "Получение данных профиля пользователя")
    @GetMapping("/profile/{username}")
    public UserProfileResponse getProfile(@PathVariable String username) {
        User user = service.getByUsername(username);
        return mapper.toProfileResponse(user);
    }

    /**
     * @brief Выдает бан пользователю.
     * @param request Запрос на выдачу бана пользователю.
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Выдача бана пользователю")
    @PutMapping("/ban")
    public void setBannedAt(@RequestBody @Valid UserBanRequest request) {
        service.setBannedAt(request);
    }

    /**
     * @brief Снимает бан с пользователя.
     * @param userId Идентификатор пользователя.
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Снятие бана с пользователя")
    @PutMapping("/unban/{userId}")
    public void resetBannedAt(@PathVariable Long userId) {
        service.resetBannedAt(userId);
    }

    /**
     * @brief Меняет пароль пользователя.
     * @param request Запрос на смену пароля пользователя.
     */
    @Operation(summary = "Смена пароля пользователя")
    @PostMapping("/change-password")
    public void changePassword(@RequestBody @Valid UpdateUserPasswordRequest request) {
        service.changePassword(request);
    }

    /**
     * @brief Пагинирует пользователей согласно заданному фильтру.
     * @param filter Фильтр для поиска пользователей.
     * @return Ответ с пагинированными пользователями.
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Получение пользователей по фильтру")
    @PostMapping("/filter")
    public PageResponse<UserResponse> findPostsByFilter(@RequestBody @Valid UserFilter filter) {
        var result = new PageResponse<UserResponse>();

        var users = service.findByFilter(filter);
        result.setTotalPages(users.getTotalPages());
        result.setTotalSize(users.getTotalElements());
        result.setPageNumber(users.getNumber());
        result.setPageSize(users.getSize());
        result.setContent(mapper.toResponse(users.getContent()));
        return result;
    }

    /**
     * @brief Получает пользователя по его идентификатору.
     * @param userId Идентификатор пользователя.
     * @return Ответ с данными пользователя.
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable Long userId) {
        User user = service.getById(userId);
        return mapper.toResponse(user);
    }

    /**
     * @brief Получает текущего пользователя.
     * @return Ответ с данными текущего пользователя.
     */
    @Operation(summary = "Получение данного пользователя")
    @GetMapping("/current")
    public UserResponse getCurrent() {
        User user = service.getCurrentUser();
        return mapper.toResponse(user);
    }

    /**
     * @brief Изменяет роль пользователя.
     * @param request Запрос на изменение роли пользователя.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Изменение роли пользователя")
    @PutMapping("/change-role")
    public void changeRole(@RequestBody @Valid UpdateUserRoleRequest request) {
        service.changeRole(request);
    }

    /**
     * @brief Проверяет, является ли пользователь суперпользователем.
     * @return Признак суперпользователя.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Проверка что пользователь суперпользователь")
    @GetMapping("/is-superuser")
    public Boolean isSuperuser() {
        return service.isSuperuser();
    }

    /**
     * @brief Удаляет пользователя.
     * @param request Запрос на удаление пользователя.
     */
    @Operation(summary = "Удаление пользователя")
    @PostMapping("/delete")
    public void deleteUser(@RequestBody @Valid DeleteUserRequest request) {
        deleteService.deleteUser(request);
    }
}
