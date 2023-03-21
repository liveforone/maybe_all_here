package maybe_all_here.userservice.service.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CircuitLog {

    MEMBER_CIRCUIT_LOG("member-circuit-breaker");

    private final String value;
}
