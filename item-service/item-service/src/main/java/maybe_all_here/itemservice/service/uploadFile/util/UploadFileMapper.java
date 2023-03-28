package maybe_all_here.itemservice.service.uploadFile.util;

import maybe_all_here.itemservice.domain.UploadFile;
import maybe_all_here.itemservice.dto.uploadFile.UploadFileRequest;

public class UploadFileMapper {

    public static UploadFile dtoToEntity(UploadFileRequest uploadFileRequest) {
        return UploadFile.builder()
                .id(uploadFileRequest.getId())
                .saveFileName(uploadFileRequest.getSaveFileName())
                .item(uploadFileRequest.getItem())
                .build();
    }
}
