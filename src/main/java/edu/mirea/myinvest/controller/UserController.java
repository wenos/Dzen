package edu.mirea.myinvest.controller;

import edu.mirea.myinvest.domain.dto.pagination.PageResponse;
import edu.mirea.myinvest.mapper.UserMapper;
import edu.mirea.myinvest.service.UserService;
import edu.mirea.myinvest.domain.dto.user.DeleteUserRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserEmailRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserInfoRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserPasswordRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserRoleRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserUsernameRequest;
import edu.mirea.myinvest.domain.dto.user.UserBanRequest;
import edu.mirea.myinvest.domain.dto.user.UserFilter;
import edu.mirea.myinvest.domain.dto.user.UserProfileResponse;
import edu.mirea.myinvest.domain.dto.user.UserResponse;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.service.DeleteService;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "Работа с пользователями")
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final DeleteService deleteService;
    private final UserMapper mapper;

    /**
     * Обновление имени пользователя
     */
    @Operation(summary = "Обновление имени пользователя")
    @PostMapping("/change-username") // TODO: Можно переписать на patch - логически подходит больше
    public void selfUpdateUsername(@RequestBody @Valid UpdateUserUsernameRequest request) {
        service.selfUpdateUsername(request);
    }

    /**
     * Обновление имени пользователя
     */
    @Operation(summary = "Обновление почты пользователя")
    @PostMapping("/change-email")
    public void selfUpdateEmail(@RequestBody @Valid UpdateUserEmailRequest request) {
        service.selfUpdateEmail(request);
    }

    @Operation(summary = "Обновление информации о пользователи")
    @PostMapping("/change-info")
    public void selfUpdateInfo(@RequestBody @Valid UpdateUserInfoRequest request) {
        service.selfUpdateInfo(request);
    }

    @Operation(summary = "Получение данных профиля пользователя")
    @GetMapping("/profile/{username}")
    public UserProfileResponse getProfile(@PathVariable String username) {
        User user = service.getByUsername(username);
        return mapper.toProfileResponse(user);
    }

    /**
     * Выдача бана пользователю
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Выдача бана пользователю")
    @PutMapping("/ban")
    public void setBannedAt(@RequestBody @Valid UserBanRequest request) {
        service.setBannedAt(request);
    }

    /**
     * Снятие бана с пользователя
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Снятие бана с пользователя")
    @PutMapping("/unban/{userId}")
    public void resetBannedAt(@PathVariable Long userId) {
        service.resetBannedAt(userId);
    }


    /**
     * Смена пароля пользователя
     */
    @Operation(summary = "Смена пароля пользователя")
    @PostMapping("/change-password")
    public void changePassword(@RequestBody @Valid UpdateUserPasswordRequest request) {
        service.changePassword(request);
    }

    /**
     * Пагинация пользователей по фильтру
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
     * Получение пользователя по id
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable Long userId) {
        User user = service.getById(userId);
        return mapper.toResponse(user);
    }


    @Operation(summary = "Получение данного пользователя")
    @GetMapping("/current")
    public UserResponse getCurrent() {
        User user = service.getCurrentUser();
        return mapper.toResponse(user);
    }


    /**
     * Изменение роли пользователя
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Изменение роли пользователя")
    @PutMapping("/change-role")
    public void changeRole(@RequestBody @Valid UpdateUserRoleRequest request) {
        service.changeRole(request);
    }

    /**
     * Проверка что пользователь суперпользователь
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Проверка что пользователь суперпользователь")
    @GetMapping("/is-superuser")
    public Boolean isSuperuser() {
        return service.isSuperuser();
    }

    @Operation(summary = "Удаление пользователя")
    @PostMapping("/delete")
    public void deleteUser(@RequestBody @Valid DeleteUserRequest request) {
        deleteService.deleteUser(request);
    }
}
