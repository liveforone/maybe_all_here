package maybe_all_here.userservice.dto.mileage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MileageResponse {

    private String email;
    private long mileagePoint;
}
