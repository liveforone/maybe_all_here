package maybe_all_here.orderservice.dto.mileage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccumulateRequest {

    private long orderPrice;
    private String email;
}
