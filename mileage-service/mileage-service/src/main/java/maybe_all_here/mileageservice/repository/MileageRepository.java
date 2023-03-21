package maybe_all_here.mileageservice.repository;

import maybe_all_here.mileageservice.domain.Mileage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage, Long>, MileageCustomRepository {
}
