package maybe_all_here.itemservice.repository.item;

import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.repository.item.ItemCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemCustomRepository {
}
