package edu.mirea.myinvest.controller;

import edu.mirea.myinvest.domain.dto.pagination.PageResponse;
import edu.mirea.myinvest.domain.dto.comment.CommentFilter;
import edu.mirea.myinvest.domain.dto.comment.CommentResponse;
import edu.mirea.myinvest.domain.dto.comment.CreateCommentRequest;
import edu.mirea.myinvest.domain.dto.comment.UpdateCommentRequest;
import edu.mirea.myinvest.domain.model.Comment;
import edu.mirea.myinvest.mapper.CommentMapper;
import edu.mirea.myinvest.service.CommentService;
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

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Работа с комментариями")
public class CommentController {
    private final CommentService service;
    private final CommentMapper mapper;

    /**
     * Создание комментария
     */
    @Operation(summary = "Создание комментария")
    @PostMapping
    public CommentResponse createComment(@RequestBody @Valid CreateCommentRequest request) {
        Comment comment = service.create(request);
        return mapper.toResponse(comment);
    }

    /**
     * Получение комментария по id
     */
    @Operation(summary = "Получение комментария по id")
    @GetMapping("/{commentId}")
    public CommentResponse getCommentById(@PathVariable Long commentId) {
        Comment comment = service.getById(commentId);
        return mapper.toResponse(comment);
    }

    /**
     * Обновление комментария
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
     * Удаление комментария по id
     */
    @Operation(summary = "Удаление комментария по id")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long commentId) {
        service.deleteById(commentId);
    }

    /**
     * Пагинация комментариев
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
