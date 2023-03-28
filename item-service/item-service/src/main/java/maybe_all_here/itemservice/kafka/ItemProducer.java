package maybe_all_here.itemservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.itemservice.dto.file.FileSaveRequest;
import maybe_all_here.itemservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    public void saveFile(List<MultipartFile> files, Long itemId) {
        FileSaveRequest request = new FileSaveRequest();
        request.setFiles(files);
        request.setItemId(itemId);
        String jsonOrder = gson.toJson(request);

        kafkaTemplate.send(Topic.SAVE_FILE, jsonOrder);
    }

    public void deleteFile(Long shopId) {
        String jsonOrder = gson.toJson(shopId);

        kafkaTemplate.send(Topic.DELETE_FILE, jsonOrder);
    }
}
