package maybe_all_here.reviewservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.reviewservice.async.AsyncConstant;
import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.Recommend;
import maybe_all_here.reviewservice.kafka.constant.KafkaLog;
import maybe_all_here.reviewservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void sendRecommendState(Review review) {
        String recommend = review.getRecommend();
        Long itemId = review.getItemId();

        if (Objects.equals(recommend, Recommend.RECOMMEND.getValue())) {
            recommendItem(itemId);
        } else {
            notRecommendItem(itemId);
        }
    }

    private void recommendItem(Long itemId) {
        String jsonOrder = gson.toJson(itemId);

        String topic = Topic.ITEM_IS_GOOD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    private void notRecommendItem(Long itemId) {
        String jsonOrder = gson.toJson(itemId);

        String topic = Topic.ITEM_IS_BAD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}
