package edu.mirea.vitality.blog.controller;

import edu.mirea.vitality.blog.domain.dto.pagination.PageResponse;
import edu.mirea.vitality.blog.domain.dto.post.PostFilter;
import edu.mirea.vitality.blog.domain.dto.post.PostRequest;
import edu.mirea.vitality.blog.mapper.PostMapper;
import edu.mirea.vitality.blog.service.PostService;
import edu.mirea.vitality.blog.domain.dto.post.PostResponse;
import edu.mirea.vitality.blog.domain.dto.post.UpdatePostRequest;
import edu.mirea.vitality.blog.domain.model.Post;
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

import java.util.List;

/**
 * @class PostController
 * @brief Этот класс представляет собой контроллер для работы с постами.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "Работа с постами")
public class PostController {

    private final PostService service;
    private final PostMapper mapper;

    /**
     * @brief Создает новый пост.
     * @param request Данные запроса для создания поста.
     * @return Созданный пост.
     */
    @Operation(summary = "Создание поста")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@Valid PostRequest request) {
        Post post = service.create(request);
        return mapper.toResponse(post);
    }

    /**
     * @brief Получает пост по его идентификатору.
     * @param postId Идентификатор поста.
     * @return Ответ с информацией о посте.
     */
    @Operation(summary = "Получение поста по id")
    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId) {
        Post post = service.getById(postId);
        return mapper.toResponse(post);
    }

    /**
     * @brief Удаляет пост по его идентификатору.
     * @param postId Идентификатор поста.
     */
    @Operation(summary = "Удаление поста по id")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId) {
        service.deleteById(postId);
    }

    /**
     * @brief Обновляет пост.
     * @param request Данные запроса для обновления поста.
     * @param postId Идентификатор поста.
     * @return Обновленный пост.
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
     * @brief Пагинирует посты согласно заданному фильтру.
     * @param filter Фильтр для поиска постов.
     * @return Ответ с пагинированными постами.
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

    /**
     * @brief Получает все посты автора по его идентификатору.
     * @param id Идентификатор автора постов.
     * @return Список постов автора.
     */
    @Operation(summary = "Получение постов по id пользователя")
    @GetMapping("/user/{id}")
    public List<PostResponse> getAllByAuthorId(@PathVariable Long id) {
        List<Post> posts = service.getAllByAuthorId(id);
        return mapper.toResponse(posts);
    }

    /**
     * @brief Ставит лайк на пост.
     * @param id Идентификатор поста.
     */
    @Operation(summary = "Лайк понравившегося поста")
    @PostMapping("/{id}/like")
    public void likePost(@PathVariable Long id) {
        service.likePost(id);
    }

    /**
     * @brief Убирает лайк с поста.
     * @param id Идентификатор поста.
     */
    @Operation(summary = "Снятие лайка с поста")
    @PostMapping("/{id}/unlike")
    public void unlikePost(@PathVariable Long id) {
        service.unlikePost(id);
    }
}