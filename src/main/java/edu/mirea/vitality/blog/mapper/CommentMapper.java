package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.comment.CommentResponse;
import edu.mirea.vitality.blog.domain.dto.user.UserResponse;
import edu.mirea.vitality.blog.domain.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @file CommentMapper.java
 * @brief Этот файл содержит интерфейс CommentMapper.
 */

/**
 * @interface CommentMapper
 * @brief Этот интерфейс предоставляет методы для преобразования объектов комментариев между сущностью и объектом ответа.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface CommentMapper {

    /**
     * @brief Преобразует сущность комментария в объект ответа комментария.
     * @param comment Сущность комментария.
     * @return Ответ комментария.
     */
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "author", source = "comment", qualifiedByName = "checkAnonymous")
    CommentResponse toResponse(Comment comment);

    /**
     * @brief Проверяет, является ли автор комментария анонимным и возвращает соответствующий ответ пользователя.
     * @param comment Комментарий.
     * @return Ответ пользователя, если автор не анонимный, иначе пустой ответ.
     */
    @Named("checkAnonymous")
    default UserResponse checkAnonymous(Comment comment) {
        return comment.getIsAnonymous() ? UserResponse.builder().build() : UserResponse.builder()
                .id(comment.getAuthor().getId())
                .username(comment.getAuthor().getUsername())
                .email(comment.getAuthor().getEmail())
                .role(comment.getAuthor().getRole())
                .bannedAt(comment.getAuthor().getBannedAt())
                .build();
    }

    /**
     * @brief Преобразует список сущностей комментариев в список объектов ответа комментариев.
     * @param comments Список сущностей комментариев.
     * @return Список ответов комментариев.
     */
    List<CommentResponse> toResponse(List<Comment> comments);
}
