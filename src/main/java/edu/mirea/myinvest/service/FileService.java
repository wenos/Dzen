package edu.mirea.myinvest.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import edu.mirea.myinvest.config.CloudStorageConfig;
import edu.mirea.myinvest.domain.dto.file.UploadedFileContentResponse;
import edu.mirea.myinvest.domain.model.UploadedFile;
import edu.mirea.myinvest.exception.file.FileDeleteProblem;
import edu.mirea.myinvest.exception.file.FileDownloadProblem;
import edu.mirea.myinvest.exception.file.FileNotFoundProblem;
import edu.mirea.myinvest.exception.file.FileSaveProblem;
import edu.mirea.myinvest.repository.UploadedFileRepository;
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
 * Управление файлами
 */
@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 yandexS3;
    private final UploadedFileRepository repository;
    private final CloudStorageConfig cloudStorageConfig;


    /**
     * Сохранение файла
     */
    private UploadedFile save(UploadedFile file) {
        return repository.save(file);
    }

    /**
     * Загрузка файлов на сервер
     *
     * @param files Массив файлов
     * @return Список идентификаторов загруженных файлов
     */
    public List<UUID> uploadFilesAndGetIds(MultipartFile[] files) {
        return Stream.of(files).map(this::saveFile).map(UploadedFile::getId).toList();
    }

    /**
     * Загрузка файлов на сервер
     *
     * @param files Массив файлов
     * @return Список загруженных файлов
     */
    public List<UploadedFile> uploadFiles(List<MultipartFile> files) {
        return files.stream().map(this::saveFile).toList();
    }


    /**
     * Сохранение файла в облаке
     *
     * @param file Файл
     * @return Сохранённый файл
     */
    private UploadedFile saveFile(MultipartFile file) {
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
     * Сохранение файла в облаке
     *
     * @param file Файл
     * @param code Код файла
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
     * Получение данных о всех файлах
     * TODO: Переделать на пагинацию, пока это не используется, оставим так
     *
     * @return Список файлов
     */
    public List<UploadedFile> getAll() {
        return repository.findAll();
    }

    /**
     * Поиск данных о файле по идентификатору
     *
     * @param id идентификатор файла
     * @return Данные о файле
     */
    public Optional<UploadedFile> findById(UUID id) {
        return repository.findById(id);
    }

    /**
     * Получение файла по идентификатору
     *
     * @param id идентификатор файла
     * @return Данные о файле
     */
    public UploadedFile getById(UUID id) {
        return findById(id).orElseThrow(
                () -> new FileNotFoundProblem(id)
        );
    }

    /**
     * Загрузка файла с облака
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
     * Удаление файла по id
     *
     * @param fileId id файла
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
