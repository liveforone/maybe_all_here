package maybe_all_here.mileageservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.mileageservice.kafka.constant.KafkaLog;
import maybe_all_here.mileageservice.kafka.constant.Topic;
import maybe_all_here.mileageservice.repository.MileageRepository;
import maybe_all_here.mileageservice.service.util.MileageMapper;
import maybe_all_here.mileageservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageConsumer {

    private final MileageRepository mileageRepository;

    @KafkaListener(topics = Topic.CREATE_MILEAGE)
    @Transactional
    public void createMileage(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        String email = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(email)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            mileageRepository.save(MileageMapper.createMileage(email));
            log.info(KafkaLog.CREATE_MILEAGE_SUCCESS.getValue());
        }
    }
}
