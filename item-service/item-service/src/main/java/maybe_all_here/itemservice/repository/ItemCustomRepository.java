package maybe_all_here.itemservice.repository;

import maybe_all_here.itemservice.dto.ItemRemainingRequest;

public interface ItemCustomRepository {

    void decreaseRemaining(ItemRemainingRequest itemRemainingRequest);

    void increaseGood(Long itemId);

    void increaseBad(Long itemId);
}
