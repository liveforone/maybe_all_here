package maybe_all_here.mileageservice.kafka.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    CREATE_MILEAGE_SUCCESS("!! Create Mileage Success !!"),
    INCREASE_MILEAGE_SUCCESS("!! Increase Mileage Success !!"),
    DECREASE_MILEAGE_SUCCESS("!! Decrease Mileage Success !!");

    private final String value;
}
