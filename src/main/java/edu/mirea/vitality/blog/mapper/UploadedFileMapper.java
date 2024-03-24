package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.file.UploadedFileResponse;
import edu.mirea.vitality.blog.domain.model.PostFileRel;
import edu.mirea.vitality.blog.domain.model.UploadedFile;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @file UploadedFileMapper.java
 * @brief Этот файл содержит интерфейс UploadedFileMapper.
 */

/**
 * @interface UploadedFileMapper
 * @brief Этот интерфейс предоставляет методы для преобразования объектов загруженных файлов между сущностью и объектом ответа.
 */
@Mapper(componentModel = "spring")
public interface UploadedFileMapper {

    /**
     * @brief Преобразует сущность загруженного файла в объект ответа загруженного файла.
     * @param uploadedFileResponse Сущность загруженного файла.
     * @return Ответ загруженного файла.
     */
    UploadedFileResponse toResponse(UploadedFile uploadedFileResponse);

    /**
     * @brief Преобразует список сущностей загруженных файлов в список объектов ответа загруженных файлов.
     * @param uploadedFileResponse Список сущностей загруженных файлов.
     * @return Список ответов загруженных файлов.
     */
    List<UploadedFileResponse> toResponse(List<UploadedFile> uploadedFileResponse);

    /**
     * @brief Преобразует список связей между постом и файлом в список загруженных файлов.
     * @param postFileRel Список связей между постом и файлом.
     * @return Список загруженных файлов.
     */
    default List<UploadedFile> toEntity(List<PostFileRel> postFileRel) {
        if (postFileRel == null) {
            return new ArrayList<>();
        }
        return postFileRel.stream().map(PostFileRel::getFile).toList();
    }

    /**
     * @brief Преобразует связь между постом и файлом в загруженный файл.
     * @param postFileRel Связь между постом и файлом.
     * @return Загруженный файл.
     */
    default UploadedFile toEntity(PostFileRel postFileRel) {
        if (postFileRel == null) {
            return null;
        }
        return postFileRel.getFile();
    }
}
