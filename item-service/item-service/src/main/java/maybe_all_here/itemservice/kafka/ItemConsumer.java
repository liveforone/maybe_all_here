package maybe_all_here.itemservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.itemservice.async.AsyncConstant;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.dto.item.ItemRemainingRequest;
import maybe_all_here.itemservice.kafka.constant.KafkaLog;
import maybe_all_here.itemservice.kafka.constant.Topic;
import maybe_all_here.itemservice.repository.item.ItemRepository;
import maybe_all_here.itemservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemConsumer {

    private final ItemRepository itemRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = Topic.ROLLBACK_REMAINING)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void rollbackRemaining(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        ItemRemainingRequest request = objectMapper.readValue(kafkaMessage, ItemRemainingRequest.class);

        if (CommonUtils.isNull(request)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Item item = itemRepository.findOneById(request.getItemId());
            item.increaseRemaining(request.getOrderQuantity());
            log.info(KafkaLog.ROLLBACK_REMAINING_SUCCESS.getValue() + item.getId());
        }
    }

    @KafkaListener(topics = Topic.DECREASE_REMAINING)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void decreaseRemaining(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        ItemRemainingRequest request = objectMapper.readValue(kafkaMessage, ItemRemainingRequest.class);

        if (CommonUtils.isNull(request)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Item item = itemRepository.findOneById(request.getItemId());
            item.decreaseRemaining(request);
            log.info(KafkaLog.DECREASE_REMAINING_SUCCESS.getValue() + item.getId());
        }
    }

    @KafkaListener(topics = Topic.ITEM_IS_GOOD)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void increaseItemGood(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long itemId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(itemId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Item item = itemRepository.findOneById(itemId);
            item.increaseGood();
            log.info(KafkaLog.INCREASE_GOOD_SUCCESS.getValue() + itemId);
        }
    }

    @KafkaListener(topics = Topic.ITEM_IS_BAD)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void increaseItemBad(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long itemId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(itemId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Item item = itemRepository.findOneById(itemId);
            item.increaseBad();
            log.info(KafkaLog.INCREASE_BAD_SUCCESS.getValue() + itemId);
        }
    }
}
