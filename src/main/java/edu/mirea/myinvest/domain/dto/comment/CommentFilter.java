package edu.mirea.myinvest.domain.dto.comment;

import edu.mirea.myinvest.domain.dto.pagination.PageRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос комментариев по фильтру")
public class CommentFilter extends PageRequestDTO {
    @Schema(description = "Пост", example = "1")
    @NotNull(message = "Id поста является обязательным параметром")
    private Long postId;
}
