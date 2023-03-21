package maybe_all_here.mileageservice.dto.mileage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageResponse {

    private String email;
    private long mileagePoint;
}
