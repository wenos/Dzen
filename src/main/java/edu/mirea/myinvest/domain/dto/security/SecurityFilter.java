package edu.mirea.myinvest.domain.dto.security;

import edu.mirea.myinvest.domain.dto.pagination.PageRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Запрос ценных бумаг по фильтру")
public class SecurityFilter extends PageRequestDTO {

    @Schema(description = "Название")
    @Size(max = 255, message = "Максимальная длина не может быть более 255 символов")
    private String name;


    @Schema(description = "Короткое название")
    @Size(max = 255, message = "Максимальная длина не может быть более 255 символов")
    private String shortname;

    @Schema(description = "Id бумаги на бирже")
    @Size(max = 255, message = "Максимальная длина не может быть более 255 символов")
    private String secId;


    @Schema(description = "Основное место торгов")
    @Size(max = 255, message = "Максимальная длина не может быть более 255 символов")
    private String primaryBoardId;

    @Schema(description = "Тип бумаги")
    private Long typeId;

    @Schema(description = "Id пользователя")
    private Long userId;

}
