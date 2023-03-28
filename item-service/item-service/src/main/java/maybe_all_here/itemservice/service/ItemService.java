package maybe_all_here.itemservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.dto.item.ItemRequest;
import maybe_all_here.itemservice.dto.item.ItemResponse;
import maybe_all_here.itemservice.repository.ItemRepository;
import maybe_all_here.itemservice.service.util.ItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> getItemHome(Long lastId, int pageSize) {
        return ItemMapper.entityToDtoList(itemRepository.findItemHome(lastId, pageSize));
    }

    public ItemResponse getItemById(Long itemId) {
        return ItemMapper.entityToDto(itemRepository.findOneById(itemId));
    }

    public List<ItemResponse> getItemsByShopId(Long shopId, Long lastId, int pageSize) {
        return ItemMapper.entityToDtoList(
                itemRepository.findItemsByShopId(shopId, lastId, pageSize)
        );
    }

    public List<ItemResponse> searchItemsByKeyword(String keyword, Long lastId, int pageSize) {
        return ItemMapper.entityToDtoList(
                itemRepository.searchItemsByKeyword(keyword, lastId, pageSize)
        );
    }

    @Transactional
    public Long createItem(ItemRequest itemRequest, Long shopId) {
        itemRequest.setShopId(shopId);

        return itemRepository
                .save(ItemMapper.dtoToEntity(itemRequest))
                .getId();
    }

    @Transactional
    public void editTitleById(String title, Long itemId) {
        itemRepository.editTitleById(title, itemId);
    }

    @Transactional
    public void editContentById(String content, Long itemId) {
        itemRepository.editContentById(content, itemId);
    }

    @Transactional
    public void editPriceById(long price, Long itemId) {
        itemRepository.editPriceById(price, itemId);
    }

    @Transactional
    public void editRemainingById(long remaining, Long itemId) {
        itemRepository.editRemainingById(remaining, itemId);
    }
}
