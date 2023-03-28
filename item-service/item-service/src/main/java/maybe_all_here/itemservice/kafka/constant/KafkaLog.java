package maybe_all_here.itemservice.kafka.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    DECREASE_REMAINING_SUCCESS("Decrease remaining success || Item id : ");

    private final String value;
}