package maybe_all_here.itemservice.repository.uploadFile;

import maybe_all_here.itemservice.domain.Item;

import java.util.List;

public interface UploadFileCustomRepository {

    List<String> findFilesByItem(Item item);
    List<String> findFilesByItemId(Long itemId);
    void deleteBulkFileByItem(Item item);
    void deleteBulkFileByItemId(Long itemId);
}
