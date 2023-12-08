package edu.mirea.myinvest.mapper;

import edu.mirea.myinvest.domain.dto.comment.CommentResponse;
import edu.mirea.myinvest.domain.dto.user.UserResponse;
import edu.mirea.myinvest.domain.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface CommentMapper {

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "author", source = "comment", qualifiedByName = "checkAnonymous")
    CommentResponse toResponse(Comment comment);

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

    List<CommentResponse> toResponse(List<Comment> comments);
}
