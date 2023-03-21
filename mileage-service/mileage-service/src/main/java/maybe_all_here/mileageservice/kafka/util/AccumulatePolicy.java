package maybe_all_here.mileageservice.kafka.util;

import maybe_all_here.mileageservice.dto.updateMileage.AccumulateRequest;

public class AccumulatePolicy {

    private static final double ACCUMULATE = 0.01;

    public static long calculateAccumulate(AccumulateRequest request) {
        return Double
                .valueOf(request.getOrderPrice() * ACCUMULATE)
                .longValue();
    }
}
