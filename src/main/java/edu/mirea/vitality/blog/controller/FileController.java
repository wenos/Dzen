package edu.mirea.vitality.blog.controller;

import edu.mirea.vitality.blog.domain.dto.file.UploadedFileResponse;
import edu.mirea.vitality.blog.domain.model.UploadedFile;
import edu.mirea.vitality.blog.mapper.UploadedFileMapper;
import edu.mirea.vitality.blog.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @class FileController
 * @brief Этот класс представляет собой контроллер для работы с файлами.
 */
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "Работа с файлами")
public class FileController {

    private final FileService service;
    private final UploadedFileMapper mapper;

    /**
     * @brief Загружает файлы на сервер.
     * @param files Массив файлов для загрузки.
     * @return Список идентификаторов загруженных файлов.
     */
    @Operation(summary = "Загрузка файла")
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UUID> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        return service.uploadFilesAndGetIds(files);
    }

    /**
     * @brief Получает все файлы на сервере.
     * @return Список всех файлов.
     */
    @Operation(summary = "Получение всех файлов")
    @GetMapping
    public List<UploadedFileResponse> getAllFiles() {
        List<UploadedFile> files = service.getAll();
        return mapper.toResponse(files);
    }

    /**
     * @brief Получает файл по его идентификатору.
     * @param fileId Идентификатор файла.
     * @return Ответ с информацией о файле.
     */
    @Operation(summary = "Получение файла по id")
    @GetMapping("/{fileId}")
    public UploadedFileResponse getFileById(@PathVariable UUID fileId) {
        UploadedFile file = service.getById(fileId);
        return mapper.toResponse(file);
    }

    /**
     * @brief Загружает файл с сервера.
     * @param fileId Идентификатор файла.
     * @return Ответ с данными файла для скачивания.
     */
    @Operation(summary = "Загрузка файла с сервера")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID fileId) {
        var file = service.downloadFile(fileId);
        HttpHeaders headers = new HttpHeaders();

        // Добавляем заголовки с информацией о файле
        headers.setContentDispositionFormData("attachment", file.name());
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(file.content().length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(file.content());
    }
}
