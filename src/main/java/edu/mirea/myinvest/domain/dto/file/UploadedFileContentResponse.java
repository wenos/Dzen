package edu.mirea.myinvest.domain.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Загруженный файл")
public record UploadedFileContentResponse(
        @Schema(description = "Имя файла")
        String name,

        @Schema(description = "Содержимое файла")
        byte[] content
) {
}
