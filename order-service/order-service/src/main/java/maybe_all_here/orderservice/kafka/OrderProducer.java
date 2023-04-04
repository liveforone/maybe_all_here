package maybe_all_here.orderservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.orderservice.kafka.constant.KafkaLog;
import maybe_all_here.orderservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();
    
//    private void recommendItem(Long itemId) {
//        String jsonOrder = gson.toJson(itemId);
//
//        String topic = Topic.ITEM_IS_GOOD;
//        kafkaTemplate.send(topic, jsonOrder);
//        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
//    }
}
