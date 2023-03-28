package maybe_all_here.itemservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.dto.item.ItemRequest;
import maybe_all_here.itemservice.repository.ItemRepository;
import maybe_all_here.itemservice.service.util.ItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long createItem(ItemRequest itemRequest, Long shopId) {
        itemRequest.setShopId(shopId);

        return itemRepository
                .save(ItemMapper.dtoToEntity(itemRequest))
                .getId();
    }
}
