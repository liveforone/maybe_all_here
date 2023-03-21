package maybe_all_here.mileageservice.kafka.util;

import maybe_all_here.mileageservice.dto.updateMileage.AccumulateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccumulatePolicyTest {

    @Test
    void calculateAccumulate() {
        //given
        long orderPrice = 43000;
        AccumulateRequest request = new AccumulateRequest();
        request.setOrderPrice(orderPrice);

        //when
        long calculatedMileage = AccumulatePolicy.calculateAccumulate(request);

        //then
        long accumulate = 430;
        Assertions
                .assertThat(calculatedMileage)
                .isEqualTo(accumulate);
    }
}