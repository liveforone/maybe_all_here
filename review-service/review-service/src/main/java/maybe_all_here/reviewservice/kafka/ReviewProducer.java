package maybe_all_here.reviewservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.reviewservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();
    
    public void recommendItem(Long itemId) {
        String jsonOrder = gson.toJson(itemId);

        kafkaTemplate.send(Topic.ITEM_IS_GOOD, jsonOrder);
    }

    public void notRecommendItem(Long itemId) {
        String jsonOrder = gson.toJson(itemId);

        kafkaTemplate.send(Topic.ITEM_IS_BAD, jsonOrder);
    }
}
