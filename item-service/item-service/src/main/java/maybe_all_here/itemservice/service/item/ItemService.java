package maybe_all_here.itemservice.service.item;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.async.AsyncConstant;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.domain.UploadFile;
import maybe_all_here.itemservice.dto.item.ItemDetailResponse;
import maybe_all_here.itemservice.dto.item.ItemProvideResponse;
import maybe_all_here.itemservice.dto.item.ItemRequest;
import maybe_all_here.itemservice.dto.item.ItemResponse;
import maybe_all_here.itemservice.repository.item.ItemRepository;
import maybe_all_here.itemservice.repository.uploadFile.UploadFileRepository;
import maybe_all_here.itemservice.service.item.util.ItemMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final UploadFileRepository uploadFileRepository;

    public List<ItemResponse> getItemHome(Long lastId, int pageSize) {
        return ItemMapper.entityToDtoList(itemRepository.findItemHome(lastId, pageSize));
    }

    public ItemResponse getItemById(Long itemId) {
        return ItemMapper.entityToDto(itemRepository.findOneById(itemId));
    }

    public ItemDetailResponse getItemDetailById(Long itemId) {
        Item item = itemRepository.findOneById(itemId);
        List<UploadFile> files = uploadFileRepository.findFilesByItem(item);

        return ItemMapper.entityToDtoDetail(item, files);
    }

    public ItemProvideResponse getItemProvideById(Long itemId) {
        return ItemMapper.entityToProvideDto(itemRepository.findOneById(itemId));
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
        Item item = Item.builder().build();
        item.create(itemRequest, shopId);

        return itemRepository.save(item).getId();
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void editTitleById(String title, Long itemId) {
        Item item = itemRepository.findOneById(itemId);
        item.updateTitle(title);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void editContentById(String content, Long itemId) {
        Item item = itemRepository.findOneById(itemId);
        item.updateContent(content);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void editPriceById(long price, Long itemId) {
        Item item = itemRepository.findOneById(itemId);
        item.updatePrice(price);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void editRemainingById(long remaining, Long itemId) {
        Item item = itemRepository.findOneById(itemId);
        item.increaseRemaining(remaining);
    }

    @Transactional
    public void deleteItemById(Long itemId) {
        itemRepository.deleteItemById(itemId);
    }
}
