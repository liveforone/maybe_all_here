package maybe_all_here.itemservice.service.uploadFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.itemservice.async.AsyncConstant;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.domain.UploadFile;
import maybe_all_here.itemservice.repository.item.ItemRepository;
import maybe_all_here.itemservice.repository.uploadFile.UploadFileRepository;
import maybe_all_here.itemservice.service.uploadFile.constant.FileLog;
import maybe_all_here.itemservice.service.uploadFile.constant.FilePathConstant;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void saveFile(List<MultipartFile> uploadFile, Long itemId) throws IOException {
        Item item = itemRepository.findOneById(itemId);
        for (MultipartFile file : uploadFile) {
            String saveFileName = makeSaveFileName(file);
            file.transferTo(new File(saveFileName));

            UploadFile uf = UploadFile.builder().build();
            uf.create(saveFileName, item);

            uploadFileRepository.save(uf);
        }
    }

    private String makeSaveFileName(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        return uuid + "_" + file.getOriginalFilename();
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void editFile(List<MultipartFile> uploadFile, Long itemId) throws IOException {
        Item item = itemRepository.findOneById(itemId);
        deleteFile(item);
        saveFile(uploadFile, itemId);
    }

    @Transactional
    private void deleteFile(Item item) {
        List<UploadFile> files = uploadFileRepository.findFilesByItem(item);

        for (UploadFile uploadFile : files) {
            String saveFileName = uploadFile.getSaveFileName();
            File file = new File(FilePathConstant.FILE_PATH + saveFileName);
            if (file.delete()) {
                log.info(FileLog.FILE_DELETE.getValue() + saveFileName);
            }
        }
        uploadFileRepository.deleteBulkFileByItem(item);
    }

    @Transactional
    public void deleteFileByItemId(Long itemId) {
        List<UploadFile> files = uploadFileRepository.findFilesByItemId(itemId);

        for (UploadFile uploadFile : files) {
            String saveFileName = uploadFile.getSaveFileName();
            File file = new File(FilePathConstant.FILE_PATH + saveFileName);
            if (file.delete()) {
                log.info(FileLog.FILE_DELETE.getValue() + saveFileName);
            }
        }
        uploadFileRepository.deleteBulkFileByItemId(itemId);
    }
}
