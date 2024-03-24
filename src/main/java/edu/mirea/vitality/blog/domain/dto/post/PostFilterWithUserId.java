package edu.mirea.vitality.blog.domain.dto.post;

import edu.mirea.vitality.blog.domain.dto.pagination.PageRequestDTO;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PostFilterWithUserId extends PageRequestDTO {

    private Long userId;
}
