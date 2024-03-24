package edu.mirea.vitality.blog.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import edu.mirea.vitality.blog.config.CloudStorageConfig;
import edu.mirea.vitality.blog.domain.dto.file.UploadedFileContentResponse;
import edu.mirea.vitality.blog.domain.model.UploadedFile;
import edu.mirea.vitality.blog.exception.file.FileDeleteProblem;
import edu.mirea.vitality.blog.exception.file.FileDownloadProblem;
import edu.mirea.vitality.blog.exception.file.FileNotFoundProblem;
import edu.mirea.vitality.blog.exception.file.FileSaveProblem;
import edu.mirea.vitality.blog.repository.UploadedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

/**
 * @class FileService
 * @brief Этот класс предоставляет сервис для работы с файлами.
 */
@Service
@RequiredArgsConstructor
public class FileService {
    private final AmazonS3 yandexS3;
    private final UploadedFileRepository repository;
    private final CloudStorageConfig cloudStorageConfig;

    /**
     * @brief Сохраняет файл.
     * @param file Файл для сохранения.
     * @return Сохраненный файл.
     */
    private UploadedFile save(UploadedFile file) {
        return repository.save(file);
    }

    /**
     * @brief Загружает файлы на сервер и возвращает их идентификаторы.
     * @param files Массив файлов для загрузки.
     * @return Список идентификаторов загруженных файлов.
     */
    public List<UUID> uploadFilesAndGetIds(MultipartFile[] files) {
        return Stream.of(files).map(this::saveFile).map(UploadedFile::getId).toList();
    }

    /**
     * @brief Загружает файлы на сервер.
     * @param files Список файлов для загрузки.
     * @return Список загруженных файлов.
     */
    public List<UploadedFile> uploadFiles(List<MultipartFile> files) {
        return files.stream().map(this::saveFile).toList();
    }

    /**
     * @brief Сохраняет файл в облаке.
     * @param file Файл для сохранения.
     * @return Сохраненный файл.
     */
    private UploadedFile saveFile(MultipartFile file) {
        // Проверяем файл и его название
        if (isNull(file) || isNull(file.getOriginalFilename())) {
            throw new FileSaveProblem();
        }
        String fileCode = UUID.randomUUID().toString();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileNameWithoutExtension = StringUtils.stripFilenameExtension(fileName);
        String extension = StringUtils.getFilenameExtension(fileName);

        saveInCloud(file, fileCode);

        return save(
                UploadedFile.builder()
                        .originalName(fileNameWithoutExtension)
                        .extension(extension)
                        .path(fileCode)
                        .size(file.getSize())
                        .build()
        );
    }

    /**
     * @brief Сохраняет файл в облаке.
     * @param file Файл для сохранения.
     * @param code Код файла.
     * @throws FileSaveProblem Если возникает проблема при сохранении файла.
     */
    public void saveInCloud(MultipartFile file, String code) throws FileSaveProblem {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes())) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            yandexS3.putObject(cloudStorageConfig.getBucketName(), code, inputStream, metadata);
        } catch (Exception e) {
            throw new FileSaveProblem();
        }
    }

    /**
     * @brief Получает список всех файлов.
     * @return Список файлов.
     */
    public List<UploadedFile> getAll() {
        return repository.findAll();
    }

    /**
     * @brief Находит файл по его идентификатору.
     * @param id Идентификатор файла.
     * @return Файл.
     */
    public Optional<UploadedFile> findById(UUID id) {
        return repository.findById(id);
    }

    /**
     * @brief Получает файл по его идентификатору.
     * @param id Идентификатор файла.
     * @return Файл.
     */
    public UploadedFile getById(UUID id) {
        return findById(id).orElseThrow(() -> new FileNotFoundProblem(id));
    }

    /**
     * @brief Загружает файл с облака.
     * @param fileId Идентификатор файла.
     * @return Данные о файле.
     */
    public UploadedFileContentResponse downloadFile(UUID fileId) {
        var file = getById(fileId);

        try {
            S3Object s3Object = yandexS3.getObject(cloudStorageConfig.getBucketName(), file.getPath());
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
            byte[] fileContent = IOUtils.toByteArray(objectInputStream);

            return UploadedFileContentResponse.builder()
                    .name(file.getOriginalName() + "." + file.getExtension())
                    .content(fileContent)
                    .build();
        } catch (Exception e) {
            throw new FileDownloadProblem(fileId);
        }
    }

    /**
     * @brief Удаляет файл по его идентификатору.
     * @param fileId Идентификатор файла.
     */
    public void deleteById(UUID fileId) {
        var uploadedFile = getById(fileId);
        try {
            yandexS3.deleteObject(cloudStorageConfig.getBucketName(), uploadedFile.getPath());
        } catch (Exception e) {
            throw new FileDeleteProblem(fileId);
        }
        repository.deleteById(fileId);
    }
}
