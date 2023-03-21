package maybe_all_here.mileageservice.repository;

import maybe_all_here.mileageservice.domain.Mileage;

public interface MileageCustomRepository {

    Mileage getMileageByEmail(String email);

    void increaseMileage(long calculatedMileage, String email);

    void decreaseMileage(long spentMileage, String email);
}
