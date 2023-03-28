package maybe_all_here.itemservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.itemservice.dto.item.ItemRemainingRequest;
import maybe_all_here.itemservice.kafka.constant.KafkaLog;
import maybe_all_here.itemservice.kafka.constant.Topic;
import maybe_all_here.itemservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemConsumer {

    //repo

    @KafkaListener(topics = Topic.DECREASE_REMAINING)
    @Transactional
    public void decreaseRemaining(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        ItemRemainingRequest request = objectMapper.readValue(kafkaMessage, ItemRemainingRequest.class);

        if (CommonUtils.isNull(request)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            //repo
            log.info(KafkaLog.DECREASE_REMAINING_SUCCESS.getValue() + request.getItemId());
        }
    }
}
