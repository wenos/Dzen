package edu.mirea.vitality.blog.service;


import edu.mirea.vitality.blog.domain.model.Post;
import edu.mirea.vitality.blog.domain.model.PostUserRel;
import edu.mirea.vitality.blog.domain.model.PostUserRelId;
import edu.mirea.vitality.blog.domain.model.User;
import edu.mirea.vitality.blog.repository.PostUserRelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @file PostUserRelService.java
 * @brief Этот файл содержит класс PostUserRelService.
 */

/**
 * @class PostUserRelService
 * @brief Класс, представляющий сервис для работы с отношениями постов и пользователей.
 */
@Service
@RequiredArgsConstructor
public class PostUserRelService {

    private final PostUserRelRepository postUserRelRepository;
    private final UserService userService;

    /**
     * Проверяет существование связи между постом и пользователем.
     * @param postId Идентификатор поста.
     * @param userId Идентификатор пользователя.
     * @return true, если связь существует, в противном случае - false.
     */
    public Boolean existsByPostIdAndUserId(Long postId, Long userId) {
        return postUserRelRepository.existsByPostIdAndUserId(postId, userId);
    }

    /**
     * Создает отношение между постом и пользователем.
     * @param post Пост.
     * @param user Пользователь.
     */
    public void create(Post post, User user) {
        PostUserRel postUserRel = new PostUserRel();
        postUserRel.setUser(user);
        postUserRel.setPost(post);
        PostUserRelId postUserRelId = new PostUserRelId();
        postUserRelId.setPostId(post.getId());
        postUserRelId.setUserId(user.getId());
        postUserRel.setId(postUserRelId);
        postUserRelRepository.save(postUserRel);
    }

    /**
     * Удаляет отношение между постом и пользователем.
     * @param postId Идентификатор поста.
     * @param userId Идентификатор пользователя.
     */
    public void delete(Long postId, Long userId) {
        postUserRelRepository.deleteByPostIdAndUserId(postId, userId);
    }
}

