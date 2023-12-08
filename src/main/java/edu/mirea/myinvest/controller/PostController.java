package edu.mirea.myinvest.controller;

import edu.mirea.myinvest.domain.dto.pagination.PageResponse;
import edu.mirea.myinvest.domain.dto.post.PostFilter;
import edu.mirea.myinvest.domain.dto.post.PostRequest;
import edu.mirea.myinvest.mapper.PostMapper;
import edu.mirea.myinvest.service.PostService;
import edu.mirea.myinvest.domain.dto.post.PostResponse;
import edu.mirea.myinvest.domain.dto.post.UpdatePostRequest;
import edu.mirea.myinvest.domain.model.Post;
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
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "Работа с постами")
public class PostController {
    private final PostService service;

    private final PostMapper mapper;

    /**
     * Создание поста
     */
    @Operation(summary = "Создание поста")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@Valid PostRequest request) {
        Post post = service.create(request);
        return mapper.toResponse(post);
    }

    /**
     * Получение поста
     */
    @Operation(summary = "Получение поста по id")
    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId) {
        Post post = service.getById(postId);
        return mapper.toResponse(post);
    }

    /**
     * Удаление поста по id
     */
    @Operation(summary = "Удаление поста по id")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId) {
        service.deleteById(postId);
    }


    /**
     * Обновление поста
     */
    @Operation(summary = "Обновление поста")
    @PutMapping("/{postId}")
    public PostResponse updatePost(
            @Valid UpdatePostRequest request,
            @PathVariable Long postId
    ) {
        Post post = service.update(request, postId);
        return mapper.toResponse(post);
    }

    /**
     * Пагинация постов по фильтру
     */
    @Operation(summary = "Получение постов по фильтру")
    @PostMapping("/filter")
    public PageResponse<PostResponse> findPostsByFilter(@RequestBody @Valid PostFilter filter) {
        var result = new PageResponse<PostResponse>();

        var posts = service.findByFilter(filter);
        result.setTotalPages(posts.getTotalPages());
        result.setTotalSize(posts.getTotalElements());
        result.setPageNumber(posts.getNumber());
        result.setPageSize(posts.getSize());
        result.setContent(mapper.toResponse(posts.getContent()));
        return result;
    }

}
