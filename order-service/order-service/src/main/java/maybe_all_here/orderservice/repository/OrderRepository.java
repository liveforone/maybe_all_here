package maybe_all_here.orderservice.repository;

import maybe_all_here.orderservice.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderCustomRepository {
}
