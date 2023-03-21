package maybe_all_here.mileageservice.repository;

import maybe_all_here.mileageservice.domain.Mileage;

public interface MileageCustomRepository {

    Mileage getMileageByEmail(String email);
}
