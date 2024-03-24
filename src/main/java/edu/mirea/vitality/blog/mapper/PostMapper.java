package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.post.PostResponse;
import edu.mirea.vitality.blog.domain.model.Post;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring", uses = {UploadedFileMapper.class, CommentMapper.class, CategoryMapper.class})
public interface PostMapper {
    @Named("toResponse")
    PostResponse toResponse(Post post);

    @IterableMapping(qualifiedByName = "toResponse")
        // т.к. withCommentsResponse наследуется от response
    List<PostResponse> toResponse(List<Post> posts);
}
