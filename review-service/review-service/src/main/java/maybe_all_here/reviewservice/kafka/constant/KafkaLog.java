package maybe_all_here.reviewservice.kafka.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_SEND_LOG("Kafka send Success | Topic : "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    REMOVE_REVIEW_BELONG_ITEM_SUCCESS("Remove review Belong item success || FK itemId : "),
    REMOVE_REVIEW_BELONG_ORDER_SUCCESS("Remove review belong order success");

    private final String value;
}
