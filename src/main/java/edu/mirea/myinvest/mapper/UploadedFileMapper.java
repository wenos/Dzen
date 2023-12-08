package edu.mirea.myinvest.mapper;

import edu.mirea.myinvest.domain.dto.file.UploadedFileResponse;
import edu.mirea.myinvest.domain.model.PostFileRel;
import edu.mirea.myinvest.domain.model.UploadedFile;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UploadedFileMapper {

    UploadedFileResponse toResponse(UploadedFile uploadedFileResponse);

    List<UploadedFileResponse> toResponse(List<UploadedFile> uploadedFileResponse);

    default List<UploadedFile> toEntity(List<PostFileRel> postFileRel) {
        if (postFileRel == null) {
            return new ArrayList<>();
        }
        return postFileRel.stream().map(PostFileRel::getFile).toList();
    }

    default UploadedFile toEntity(PostFileRel postFileRel) {
        if (postFileRel == null) {
            return null;
        }
        return postFileRel.getFile();
    }
}
