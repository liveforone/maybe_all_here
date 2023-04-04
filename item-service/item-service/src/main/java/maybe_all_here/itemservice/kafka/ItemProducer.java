package maybe_all_here.itemservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.itemservice.kafka.constant.KafkaLog;
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

        String topic = Topic.REMOVE_REVIEW_BELONG_ITEM;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}
