package maybe_all_here.mileageservice.kafka.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccumulatePolicyTest {

    @Test
    void calculateAccumulate() {
        //given
        long orderPrice = 43000;

        //when
        long calculatedMileage = AccumulatePolicy.calculateAccumulate(orderPrice);

        //then
        long accumulate = 430;
        Assertions
                .assertThat(calculatedMileage)
                .isEqualTo(accumulate);
    }
}