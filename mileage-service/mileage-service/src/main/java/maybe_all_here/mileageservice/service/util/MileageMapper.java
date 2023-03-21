package maybe_all_here.mileageservice.service.util;

import maybe_all_here.mileageservice.domain.Mileage;
import maybe_all_here.mileageservice.dto.MileageResponse;

public class MileageMapper {

    public static Mileage createMileage(String email) {
        return Mileage.builder()
                .email(email)
                .build();
    }

    public static MileageResponse entityToDto(Mileage mileage) {
        return MileageResponse.builder()
                .email(mileage.getEmail())
                .mileagePoint(mileage.getMileagePoint())
                .build();
    }
}
