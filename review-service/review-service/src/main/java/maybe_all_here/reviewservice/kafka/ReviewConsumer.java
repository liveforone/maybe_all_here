package maybe_all_here.reviewservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.reviewservice.kafka.constant.KafkaLog;
import maybe_all_here.reviewservice.kafka.constant.Topic;
import maybe_all_here.reviewservice.repository.ReviewRepository;
import maybe_all_here.reviewservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewConsumer {

    private final ReviewRepository reviewRepository;

    @KafkaListener(topics = Topic.REMOVE_BELONG_REVIEW)
    @Transactional
    public void removeBelongReview(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        Long itemId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(itemId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            reviewRepository.deleteBulkByItemId(itemId);
            log.info(KafkaLog.REMOVE_ALL_BELONG_REVIEW_SUCCESS.getValue() + itemId);
        }
    }
}
