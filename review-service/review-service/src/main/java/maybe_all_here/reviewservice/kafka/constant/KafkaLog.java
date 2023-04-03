package maybe_all_here.reviewservice.kafka.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    REMOVE_ALL_BELONG_REVIEW_SUCCESS("Remove All Belong review success || FK itemId : ");

    private final String value;
}
