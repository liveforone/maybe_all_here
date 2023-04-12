package maybe_all_here.mileageservice.service.util;

import maybe_all_here.mileageservice.domain.Mileage;
import maybe_all_here.mileageservice.dto.mileage.MileageResponse;

public class MileageMapper {

    public static MileageResponse entityToDto(Mileage mileage) {
        return MileageResponse.builder()
                .email(mileage.getEmail())
                .mileagePoint(mileage.getMileagePoint())
                .build();
    }
}
