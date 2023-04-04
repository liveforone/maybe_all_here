package maybe_all_here.orderservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.orderservice.dto.item.ItemRemainingRequest;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.dto.review.RemoveReviewBelongOrderRequest;
import maybe_all_here.orderservice.kafka.constant.KafkaLog;
import maybe_all_here.orderservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    public void decreaseRemaining(OrderRequest orderRequest) {
        ItemRemainingRequest request = ItemRemainingRequest.builder()
                .itemId(orderRequest.getItemId())
                .orderQuantity(orderRequest.getOrderQuantity())
                .build();

        String jsonOrder = gson.toJson(request);
        String topic = Topic.DECREASE_REMAINING;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
    
    public void removeReviewBelongOrder(String email, Long itemId) {
        RemoveReviewBelongOrderRequest request = RemoveReviewBelongOrderRequest.builder()
                .email(email)
                .itemId(itemId)
                .build();

        String jsonOrder = gson.toJson(request);
        String topic = Topic.REMOVE_REVIEW_BELONG_ORDER;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}
