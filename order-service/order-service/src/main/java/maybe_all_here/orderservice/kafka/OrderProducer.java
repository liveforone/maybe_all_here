package maybe_all_here.orderservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.orderservice.dto.item.ItemRemainingRequest;
import maybe_all_here.orderservice.dto.mileage.AccumulateRequest;
import maybe_all_here.orderservice.dto.mileage.UsingMileageRequest;
import maybe_all_here.orderservice.dto.order.OrderRequest;
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

    public void increaseMileage(OrderRequest orderRequest) {
        AccumulateRequest request = AccumulateRequest.builder()
                .orderPrice(orderRequest.getTotalPrice())
                .email(orderRequest.getEmail())
                .build();

        String jsonOrder = gson.toJson(request);
        String topic = Topic.INCREASE_MILEAGE;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    public void decreaseMileage(OrderRequest orderRequest) {
        UsingMileageRequest request = UsingMileageRequest.builder()
                .spentMileage(orderRequest.getSpentMileage())
                .email(orderRequest.getEmail())
                .build();

        String jsonOrder = gson.toJson(request);
        String topic = Topic.DECREASE_MILEAGE;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
    
    public void removeReviewBelongOrder(Long orderId) {
        String jsonOrder = gson.toJson(orderId);
        String topic = Topic.REMOVE_REVIEW_BELONG_ORDER;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}
