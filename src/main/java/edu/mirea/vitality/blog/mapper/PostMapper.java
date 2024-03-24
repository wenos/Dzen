package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.post.PostResponse;
import edu.mirea.vitality.blog.domain.model.Post;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;


/**
 * @file PostMapper.java
 * @brief Этот файл содержит интерфейс PostMapper.
 */

/**
 * @interface PostMapper
 * @brief Этот интерфейс предоставляет методы для преобразования объектов постов между сущностью и объектом ответа.
 */
@Mapper(componentModel = "spring", uses = {UploadedFileMapper.class, CommentMapper.class, CategoryMapper.class})
public interface PostMapper {

    /**
     * @brief Преобразует сущность поста в объект ответа поста.
     * @param post Сущность поста.
     * @return Ответ поста.
     */
    @Named("toResponse")
    PostResponse toResponse(Post post);

    /**
     * @brief Преобразует список сущностей постов в список объектов ответа постов.
     * @param posts Список сущностей постов.
     * @return Список ответов постов.
     */
    @IterableMapping(qualifiedByName = "toResponse")
    // т.к. withCommentsResponse наследуется от response
    List<PostResponse> toResponse(List<Post> posts);
}
