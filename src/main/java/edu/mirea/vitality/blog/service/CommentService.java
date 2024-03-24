package edu.mirea.vitality.blog.service;

import edu.mirea.vitality.blog.domain.dto.comment.CommentFilter;
import edu.mirea.vitality.blog.domain.dto.comment.CreateCommentRequest;
import edu.mirea.vitality.blog.domain.dto.comment.UpdateCommentRequest;
import edu.mirea.vitality.blog.domain.model.User;
import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.domain.model.Comment;
import edu.mirea.vitality.blog.exception.comment.CommentNotFoundProblem;
import edu.mirea.vitality.blog.exception.user.ForbiddenAccessProblem;
import edu.mirea.vitality.blog.repository.CommentRepository;
import edu.mirea.vitality.blog.service.system.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * @file CommentService.java
 * @brief Этот файл содержит класс CommentService.
 */

/**
 * @class CommentService
 * @brief Этот класс предоставляет сервисные методы для работы с комментариями.
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final PostService postService;
    private final UserService userService;
    private final ConfigService configService;

    /**
     * @brief Сохраняет комментарий.
     * @param comment Комментарий для сохранения.
     * @return Сохраненный комментарий.
     */
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    /**
     * @brief Создает новый комментарий.
     * @param request Запрос на создание комментария.
     * @return Созданный комментарий.
     */
    @Transactional
    public Comment create(CreateCommentRequest request) {
        var post = postService.getById(request.postId());
        var user = userService.getCurrentUser();
        return save(
                Comment.builder()
                        .content(request.content())
                        .post(post)
                        .author(user)
                        .isAnonymous(request.isAnonymous())
                        .build()
        );
    }

    /**
     * @brief Получает комментарий по его идентификатору.
     * @param id Идентификатор комментария.
     * @return Комментарий с заданным идентификатором.
     */
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * @brief Получает комментарий по его идентификатору.
     * @param commentId Идентификатор комментария.
     * @return Комментарий с заданным идентификатором.
     * @throws CommentNotFoundProblem Если комментарий не найден.
     */
    public Comment getById(Long commentId) {
        return findById(commentId).orElseThrow(() -> new CommentNotFoundProblem(commentId));
    }

    /**
     * @brief Обновляет информацию о комментарии.
     * @param request Запрос на обновление комментария.
     * @param commentId Идентификатор комментария.
     * @return Обновленный комментарий.
     */
    public Comment update(UpdateCommentRequest request, Long commentId) {
        User currentUser = userService.getCurrentUser();
        Comment comment = getById(commentId);

        if (!comment.isAuthor(currentUser)) {
            throw new ForbiddenAccessProblem();
        }

        comment.setContent(request.content());
        return save(comment);
    }

    /**
     * @brief Удаляет комментарий по его идентификатору.
     * @param commentId Идентификатор комментария.
     */
    public void deleteById(Long commentId) {
        User currentUser = userService.getCurrentUser();
        Comment comment = getById(commentId);

        if (!comment.isAuthor(currentUser) && !currentUser.isModerator()) {
            throw new ForbiddenAccessProblem();
        }

        repository.delete(comment);
    }

    /**
     * @brief Поиск комментариев по идентификатору поста.
     * @param filter Фильтр.
     * @return Найденные комментарии.
     */
    public Page<Comment> findByPostId(CommentFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return repository.findAllByPostId(filter.getPostId(), pageable);
    }

    /**
     * @brief Удаляет устаревшие комментарии.
     */
    @Transactional
    public void deleteOldComments() {
        var config = configService.getProperty(SystemPropertyKey.QUARTZ_OLD_COMMENT_DELETE_MINUTES);
        var timestamp = OffsetDateTime.now().minusMinutes(config.getLongValue());
        repository.deleteOldComments(timestamp);
    }

    /**
     * @brief Удаляет комментарии по идентификатору автора.
     * @param id Идентификатор автора.
     */
    @Transactional
    public void deleteByAuthorId(Long id) {
        repository.deleteAllByAuthorId(id);
    }
}

