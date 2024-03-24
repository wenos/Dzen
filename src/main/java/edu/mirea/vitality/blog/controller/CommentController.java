package edu.mirea.vitality.blog.controller;

import edu.mirea.vitality.blog.domain.dto.pagination.PageResponse;
import edu.mirea.vitality.blog.domain.dto.comment.CommentFilter;
import edu.mirea.vitality.blog.domain.dto.comment.CommentResponse;
import edu.mirea.vitality.blog.domain.dto.comment.CreateCommentRequest;
import edu.mirea.vitality.blog.domain.dto.comment.UpdateCommentRequest;
import edu.mirea.vitality.blog.domain.model.Comment;
import edu.mirea.vitality.blog.mapper.CommentMapper;
import edu.mirea.vitality.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @class CommentController
 * @brief Этот класс представляет собой контроллер для управления комментариями.
 */
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Работа с комментариями")
public class CommentController {

    private final CommentService service;
    private final CommentMapper mapper;

    /**
     * @brief Создает комментарий.
     * @param request Данные запроса для создания комментария.
     * @return Созданный комментарий.
     */
    @Operation(summary = "Создание комментария")
    @PostMapping
    public CommentResponse createComment(@RequestBody @Valid CreateCommentRequest request) {
        Comment comment = service.create(request);
        return mapper.toResponse(comment);
    }

    /**
     * @brief Получает комментарий по его идентификатору.
     * @param commentId Идентификатор комментария.
     * @return Комментарий.
     */
    @Operation(summary = "Получение комментария по id")
    @GetMapping("/{commentId}")
    public CommentResponse getCommentById(@PathVariable Long commentId) {
        Comment comment = service.getById(commentId);
        return mapper.toResponse(comment);
    }

    /**
     * @brief Обновляет комментарий.
     * @param request Данные запроса для обновления комментария.
     * @param commentId Идентификатор комментария.
     * @return Обновленный комментарий.
     */
    @Operation(summary = "Обновление комментария")
    @PutMapping("/{commentId}")
    public CommentResponse updateComment(
            @RequestBody @Valid UpdateCommentRequest request,
            @PathVariable Long commentId
    ) {
        Comment comment = service.update(request, commentId);
        return mapper.toResponse(comment);
    }

    /**
     * @brief Удаляет комментарий по его идентификатору.
     * @param commentId Идентификатор комментария.
     */
    @Operation(summary = "Удаление комментария по id")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long commentId) {
        service.deleteById(commentId);
    }

    /**
     * @brief Пагинирует комментарии.
     * @param filter Фильтр для поиска комментариев.
     * @return Ответ с пагинированными комментариями.
     */
    @Operation(summary = "Получение комментариев для поста")
    @PostMapping("/post")
    public PageResponse<CommentResponse> findCommentsByPostId(@RequestBody @Valid CommentFilter filter) {
        var comments = service.findByPostId(filter);

        var result = new PageResponse<CommentResponse>();
        result.setTotalPages(comments.getTotalPages());
        result.setTotalSize(comments.getTotalElements());
        result.setPageNumber(comments.getNumber());
        result.setPageSize(comments.getSize());
        result.setContent(mapper.toResponse(comments.getContent()));
        return result;
    }
}
