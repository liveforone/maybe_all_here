package maybe_all_here.itemservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.itemservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    public void removeBelongReview(Long itemId) {
        String jsonOrder = gson.toJson(itemId);

        kafkaTemplate.send(Topic.REMOVE_BELONG_REVIEW, jsonOrder);
    }
}
