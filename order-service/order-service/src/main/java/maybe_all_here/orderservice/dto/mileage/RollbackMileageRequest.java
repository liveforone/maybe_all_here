package maybe_all_here.orderservice.dto.mileage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollbackMileageRequest {
    private long totalPrice;
    private long spentMileage;
    private String email;
}
