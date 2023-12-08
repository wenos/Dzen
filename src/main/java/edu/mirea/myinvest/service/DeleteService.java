package edu.mirea.myinvest.service;

import edu.mirea.myinvest.config.security.SuperUserConfig;
import edu.mirea.myinvest.domain.dto.user.DeleteUserRequest;
import edu.mirea.myinvest.exception.user.ForbiddenAccessProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DeleteService {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final SuperUserConfig superUserConfig;


    /**
     * Удаление пользователя
     *
     * @param request Запрос на удаление пользователя
     */
    public void deleteUser(DeleteUserRequest request) {
        // Проверяем если права на удаление пользователя
        var currentUser = userService.getCurrentUser();
        var userForDelete = userService.getById(request.userId());

        // Если это не сам пользователь, проверяем
        if (!currentUser.getId().equals(userForDelete.getId())) {

            // Если не админ, то кидаем ошибку
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

        if (request.deleteComments()) {
            commentService.deleteByAuthorId(userForDelete.getId());
            userForDelete.setComments(null);
        }

        if (request.deletePosts()) {
            postService.deleteByAuthorId(userForDelete.getId());
            userForDelete.setPosts(null);
        }

        userForDelete.setDeletedAt(OffsetDateTime.now());
        userService.save(userForDelete);
    }
}
