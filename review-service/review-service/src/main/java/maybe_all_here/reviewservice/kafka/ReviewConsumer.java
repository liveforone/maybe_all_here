package maybe_all_here.reviewservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.reviewservice.async.AsyncConstant;
import maybe_all_here.reviewservice.kafka.constant.KafkaLog;
import maybe_all_here.reviewservice.kafka.constant.Topic;
import maybe_all_here.reviewservice.repository.ReviewRepository;
import maybe_all_here.reviewservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewConsumer {

    private final ReviewRepository reviewRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = Topic.REMOVE_REVIEW_BELONG_ORDER)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void removeReviewBelongOrder(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long orderId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(orderId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            reviewRepository.deleteOneByOrderId(orderId);
            log.info(KafkaLog.REMOVE_REVIEW_BELONG_ORDER_SUCCESS.getValue());
        }
    }

    @KafkaListener(topics = Topic.REMOVE_REVIEW_BELONG_ITEM)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void removeReviewBelongItem(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long itemId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(itemId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            reviewRepository.deleteBulkByItemId(itemId);
            log.info(KafkaLog.REMOVE_REVIEW_BELONG_ITEM_SUCCESS.getValue() + itemId);
        }
    }
}
