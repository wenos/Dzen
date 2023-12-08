package edu.mirea.myinvest.service;

import edu.mirea.myinvest.domain.dto.comment.CommentFilter;
import edu.mirea.myinvest.domain.dto.comment.CreateCommentRequest;
import edu.mirea.myinvest.domain.dto.comment.UpdateCommentRequest;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.domain.model.system.SystemPropertyKey;
import edu.mirea.myinvest.domain.model.Comment;
import edu.mirea.myinvest.exception.comment.CommentNotFoundProblem;
import edu.mirea.myinvest.exception.user.ForbiddenAccessProblem;
import edu.mirea.myinvest.repository.CommentRepository;
import edu.mirea.myinvest.service.system.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final PostService postService;
    private final UserService userService;
    private final ConfigService configService;

    /**
     * Сохранение комментария
     */
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    /**
     * Создание комментария
     *
     * @param request запрос на создание комментария
     * @return созданный комментарий
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
     * Поиск комментария по идентификатору
     *
     * @param id идентификатор комментария
     * @return комментарий
     */
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Получение комментария по идентификатору
     *
     * @param commentId идентификатор комментария
     * @return комментарий
     */
    public Comment getById(Long commentId) {
        return findById(commentId).orElseThrow(() -> new CommentNotFoundProblem(commentId));
    }

    /**
     * Обновление комментария
     *
     * @param request   запрос на обновление комментария
     * @param commentId id комментария
     * @return обновленный комментарий
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
     * Удаление комментария по id
     *
     * @param commentId id комментария
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
     * Поиск комментариев по идентификатору поста
     *
     * @param filter фильтр
     * @return Найденные посты
     */
    public Page<Comment> findByPostId(CommentFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        return repository.findAllByPostId(filter.getPostId(), pageable);
    }


    /**
     * Удаление устаревших комментариев
     */
    @Transactional
    public void deleteOldComments() {
        var config = configService.getProperty(SystemPropertyKey.QUARTZ_OLD_COMMENT_DELETE_MINUTES);
        var timestamp = OffsetDateTime.now().minusMinutes(config.getLongValue());
        repository.deleteOldComments(timestamp);
    }

    /**
     * Удаление комментариев по id автора
     *
     * @param id id автора
     */
    @Transactional
    public void deleteByAuthorId(Long id) {
        repository.deleteAllByAuthorId(id);
    }
}
