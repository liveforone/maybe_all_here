package maybe_all_here.reviewservice.repository;

import maybe_all_here.reviewservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
