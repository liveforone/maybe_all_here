package may_all_here.shopservice.repository;

import may_all_here.shopservice.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopCustomRepository {
}
