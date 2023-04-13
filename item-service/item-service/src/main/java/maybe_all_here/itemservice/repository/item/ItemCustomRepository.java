package maybe_all_here.itemservice.repository.item;

import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.dto.item.ItemRemainingRequest;

import java.util.List;

public interface ItemCustomRepository {

    List<Item> findItemHome(Long lastId, int pageSize);
    Item findOneById(Long itemId);
    List<Item> findItemsByShopId(Long shopId, Long lastId, int pageSize);
    List<Item> searchItemsByKeyword(String keyword, Long lastId, int pageSize);
    void deleteItemById(Long itemId);
    void increaseBad(Long itemId);
}
