package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.auth.SignUpRequest;
import edu.mirea.vitality.blog.domain.dto.user.UserProfileResponse;
import edu.mirea.vitality.blog.domain.dto.user.UserResponse;
import edu.mirea.vitality.blog.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @file UserMapper.java
 * @brief Этот файл содержит интерфейс UserMapper.
 */

/**
 * @interface UserMapper
 * @brief Этот интерфейс предоставляет методы для преобразования объектов пользователя между сущностью и объектом ответа.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * @brief Преобразует сущность пользователя в объект ответа пользователя.
     * @param user Сущность пользователя.
     * @return Ответ пользователя.
     */
    UserResponse toResponse(User user);

    /**
     * @brief Преобразует сущность пользователя в объект ответа профиля пользователя.
     * @param user Сущность пользователя.
     * @return Ответ профиля пользователя.
     */
    UserProfileResponse toProfileResponse(User user);

    /**
     * @brief Преобразует список сущностей пользователей в список объектов ответа пользователей.
     * @param users Список сущностей пользователей.
     * @return Список ответов пользователей.
     */
    List<UserResponse> toResponse(List<User> users);

    /**
     * @brief Преобразует объект запроса на регистрацию пользователя в сущность пользователя.
     * @param request Запрос на регистрацию пользователя.
     * @return Сущность пользователя.
     */
    User toEntity(SignUpRequest request);
}
