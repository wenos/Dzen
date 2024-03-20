package edu.mirea.myinvest.domain.dto.post;

import edu.mirea.myinvest.domain.dto.pagination.PageRequestDTO;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PostFilter extends PageRequestDTO {

    private Long categoryId;

    private String title;


}
