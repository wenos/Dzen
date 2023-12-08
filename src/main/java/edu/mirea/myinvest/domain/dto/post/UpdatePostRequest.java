package edu.mirea.myinvest.domain.dto.post;

import edu.mirea.myinvest.validation.constraints.ValidFiles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Builder
@Schema(description = "Запрос на обновление поста")
public record UpdatePostRequest(
        @Schema(description = "Заголовок поста", example = "Коллоквиум по Java по асинхронному программированию")
        @Size(min = 5, max = 255, message = "Заголовок должен быть от 5 до 255 символов")
        @NotBlank(message = "Заголовок не должен быть пустым")
        String title,

        @Schema(description = "Содержание поста", example = "Коллоквиум по Java по асинхронному программированию состоится 20.05.2021 в 10:00")
        @Size(max = 9_999, message = "Содержание должно быть от 0 до 9999 символов")
        String content,

//        @Schema(description = "Id типа поста", example = "1")
//        Long postTypeId,
//
//        @Schema(description = "Id топика", example = "1")
//        Long topicId,

        @Schema(description = "Файлы для удаления")
        List<UUID> deletedAttachments,

        @Schema(description = "Новые файлы")
        @ValidFiles
        List<MultipartFile> attachments
) {
}
