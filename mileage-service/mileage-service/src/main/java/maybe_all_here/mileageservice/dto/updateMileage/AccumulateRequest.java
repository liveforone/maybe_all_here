package maybe_all_here.mileageservice.dto.updateMileage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccumulateRequest {

    private long orderPrice;
    private String email;
}
