package maybe_all_here.reviewservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

//    public void createMileage(String email) {
//        String jsonOrder = gson.toJson(email);
//
//        kafkaTemplate.send(Topic.CREATE_MILEAGE, jsonOrder);
//    }
}