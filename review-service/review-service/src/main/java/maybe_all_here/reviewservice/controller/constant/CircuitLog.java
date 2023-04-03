package maybe_all_here.reviewservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CircuitLog {

    REVIEW_CIRCUIT_LOG("review-circuit-breaker");

    private final String value;
}
