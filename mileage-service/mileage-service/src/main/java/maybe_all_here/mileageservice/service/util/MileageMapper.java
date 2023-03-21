package maybe_all_here.mileageservice.service.util;

import maybe_all_here.mileageservice.domain.Mileage;

public class MileageMapper {

    public static Mileage createMileage(String email) {
        return Mileage.builder()
                .email(email)
                .build();
    }
}
