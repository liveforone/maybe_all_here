package maybe_all_here.mileageservice.dto.updateMileage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollbackMileageRequest {
    private long spentMileage;
    private String email;
}
