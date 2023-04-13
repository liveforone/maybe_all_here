package maybe_all_here.orderservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.orderservice.async.AsyncConstant;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.item.ItemRemainingRequest;
import maybe_all_here.orderservice.dto.mileage.AccumulateRequest;
import maybe_all_here.orderservice.dto.mileage.UsingMileageRequest;
import maybe_all_here.orderservice.kafka.constant.KafkaLog;
import maybe_all_here.orderservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void rollbackRemaining(Orders orders) {
        ItemRemainingRequest request = ItemRemainingRequest.builder()
                .itemId(orders.getItemId())
                .orderQuantity(orders.getOrderQuantity())
                .build();

        String jsonOrder = gson.toJson(request);
        String topic = Topic.ROLLBACK_REMAINING;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void decreaseRemaining(Orders orders) {
        ItemRemainingRequest request = ItemRemainingRequest.builder()
                .itemId(orders.getItemId())
                .orderQuantity(orders.getOrderQuantity())
                .build();

        String jsonOrder = gson.toJson(request);
        String topic = Topic.DECREASE_REMAINING;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void increaseMileage(Orders orders) {
        AccumulateRequest request = AccumulateRequest.builder()
                .orderPrice(orders.getTotalPrice())
                .email(orders.getEmail())
                .build();

        String jsonOrder = gson.toJson(request);
        String topic = Topic.INCREASE_MILEAGE;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void decreaseMileage(Orders orders) {
        UsingMileageRequest request = UsingMileageRequest.builder()
                .spentMileage(orders.getSpentMileage())
                .email(orders.getEmail())
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
