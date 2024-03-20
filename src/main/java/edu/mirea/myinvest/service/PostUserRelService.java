package edu.mirea.myinvest.service;


import edu.mirea.myinvest.domain.model.Post;
import edu.mirea.myinvest.domain.model.PostUserRel;
import edu.mirea.myinvest.domain.model.PostUserRelId;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.repository.PostUserRelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUserRelService {

    private final PostUserRelRepository postUserRelRepository;

    private final UserService userService;


    public Boolean existsByPostIdAndUserId(Long postId, Long userId) {
        return postUserRelRepository.existsByPostIdAndUserId(postId, userId);
    }

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

    public void delete(Long postId, Long userId) {
        postUserRelRepository.deleteByPostIdAndUserId(postId, userId);
    }


}
