package maybe_all_here.mileageservice.kafka.util;

public class AccumulatePolicy {

    private static final double ACCUMULATE = 0.01;

    public static long calculateAccumulate(long orderPrice) {
        return Double
                .valueOf(orderPrice * ACCUMULATE)
                .longValue();
    }
}
