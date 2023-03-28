package maybe_all_here.itemservice.repository;

import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.dto.item.ItemRemainingRequest;

import java.util.List;

public interface ItemCustomRepository {

    List<Item> findItemHome(Long lastId, int pageSize);
    void decreaseRemaining(ItemRemainingRequest itemRemainingRequest);

    void increaseGood(Long itemId);

    void increaseBad(Long itemId);
}
