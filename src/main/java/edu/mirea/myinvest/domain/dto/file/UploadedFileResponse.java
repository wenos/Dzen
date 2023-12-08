package edu.mirea.myinvest.domain.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Информация о загруженном файле")
public record UploadedFileResponse(
        @Schema(description = "Идентификатор файла", example = "00000000-0000-0000-0000-000000000000")
        UUID id,

        @Schema(description = "Оригинальное имя файла", example = "filename")
        String originalName,

        @Schema(description = "Расширение файла", example = "txt")
        String extension,

        @Schema(description = "Размер файла", example = "1024")
        Long size,

        @Schema(description = "Код файла", example = "00000000-0000-0000-0000-000000000000")
        String path,

        @Schema(description = "Время загрузки файла", example = "2023-10-10T10:10:10.000+00:00")
        OffsetDateTime createdAt,

        @Schema(description = "Время обновления файла", example = "2024-10-10T10:10:10.000+00:00")
        OffsetDateTime updatedAt
) {
}
