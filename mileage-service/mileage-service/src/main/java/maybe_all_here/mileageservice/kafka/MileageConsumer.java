package maybe_all_here.mileageservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.mileageservice.async.AsyncConstant;
import maybe_all_here.mileageservice.domain.Mileage;
import maybe_all_here.mileageservice.dto.updateMileage.AccumulateRequest;
import maybe_all_here.mileageservice.dto.updateMileage.RollbackMileageRequest;
import maybe_all_here.mileageservice.dto.updateMileage.UsingMileageRequest;
import maybe_all_here.mileageservice.kafka.constant.KafkaLog;
import maybe_all_here.mileageservice.kafka.constant.Topic;
import maybe_all_here.mileageservice.kafka.util.AccumulatePolicy;
import maybe_all_here.mileageservice.repository.MileageRepository;
import maybe_all_here.mileageservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageConsumer {

    private final MileageRepository mileageRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = Topic.CREATE_MILEAGE)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void createMileage(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        String email = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(email)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Mileage mileage = Mileage.builder().build();
            mileage.create(email);

            mileageRepository.save(mileage);
            log.info(KafkaLog.CREATE_MILEAGE_SUCCESS.getValue());
        }
    }

    @KafkaListener(topics = Topic.INCREASE_MILEAGE)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void increaseMileage(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        AccumulateRequest request = objectMapper.readValue(kafkaMessage, AccumulateRequest.class);
        long calculatedMileage = AccumulatePolicy.calculateAccumulate(request.getOrderPrice());

        Mileage mileage = mileageRepository.findOneByEmail(request.getEmail());
        mileage.increaseMileage(calculatedMileage);

        log.info(KafkaLog.INCREASE_MILEAGE_SUCCESS.getValue());
    }

    @KafkaListener(topics = Topic.DECREASE_MILEAGE)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void decreaseMileage(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        UsingMileageRequest request = objectMapper.readValue(kafkaMessage, UsingMileageRequest.class);

        Mileage mileage = mileageRepository.findOneByEmail(request.getEmail());
        mileage.decreaseMileage(request.getSpentMileage());

        log.info(KafkaLog.DECREASE_MILEAGE_SUCCESS.getValue());
    }

    @KafkaListener(topics = Topic.ROLLBACK_MILEAGE)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void rollbackMileage(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        RollbackMileageRequest request = objectMapper.readValue(kafkaMessage, RollbackMileageRequest.class);
        long calculatedMileage = AccumulatePolicy.calculateAccumulate(request.getTotalPrice());

        Mileage mileage = mileageRepository.findOneByEmail(request.getEmail());
        mileage.decreaseMileage(calculatedMileage);
        mileage.increaseMileage(request.getSpentMileage());

        log.info(KafkaLog.ROLLBACK_MILEAGE_SUCCESS.getValue());
    }
}
