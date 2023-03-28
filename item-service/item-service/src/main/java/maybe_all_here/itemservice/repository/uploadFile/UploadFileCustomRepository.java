package maybe_all_here.itemservice.repository.uploadFile;

import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.domain.UploadFile;

import java.util.List;

public interface UploadFileCustomRepository {

    List<UploadFile> findFilesByItem(Item item);
    List<UploadFile> findFilesByItemId(Long itemId);
    void deleteBulkFileByItem(Item item);
    void deleteBulkFileByItemId(Long itemId);
}
