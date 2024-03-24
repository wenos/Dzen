package edu.mirea.vitality.blog.service;

import edu.mirea.vitality.blog.config.security.SuperUserConfig;
import edu.mirea.vitality.blog.domain.dto.user.DeleteUserRequest;
import edu.mirea.vitality.blog.exception.user.ForbiddenAccessProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

/**
 * @file DeleteService.java
 * @brief Этот файл содержит класс DeleteService.
 */

/**
 * @class DeleteService
 * @brief Этот класс предоставляет сервис для удаления пользователей и связанных с ними данных.
 */
@Service
@RequiredArgsConstructor
public class DeleteService {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final SuperUserConfig superUserConfig;

    /**
     * @brief Удаляет пользователя и связанные с ним данные.
     * @param request Запрос на удаление пользователя.
     * @throws ForbiddenAccessProblem Если текущий пользователь не имеет прав на удаление пользователя или удаление суперпользователя.
     */
    public void deleteUser(DeleteUserRequest request) {
        // Проверяем права на удаление пользователя
        var currentUser = userService.getCurrentUser();
        var userForDelete = userService.getById(request.userId());

        // Если это не сам пользователь, проверяем
        if (!currentUser.getId().equals(userForDelete.getId())) {

            // Если не админ, кидаем ошибку
            if (!currentUser.isAdmin()) {
                throw new ForbiddenAccessProblem();
            }

            // Если админ, то проверяем, что удаляемый пользователь не админ
            if (!userService.hasAccessToUser(currentUser, userForDelete)) {
                throw new ForbiddenAccessProblem();
            }
        }

        // Суперпользователь не может удалиться
        if (superUserConfig.isSuperuser(userForDelete.getId())) {
            throw new ForbiddenAccessProblem();
        }

        // Удаление комментариев, если указано в запросе
        if (request.deleteComments()) {
            commentService.deleteByAuthorId(userForDelete.getId());
            userForDelete.setComments(null);
        }

        // Удаление постов, если указано в запросе
        if (request.deletePosts()) {
            postService.deleteByAuthorId(userForDelete.getId());
            userForDelete.setPosts(null);
        }

        userForDelete.setDeletedAt(OffsetDateTime.now());
        userService.save(userForDelete);
    }
}

