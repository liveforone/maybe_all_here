package maybe_all_here.itemservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.repository.item.ItemRepository;
import maybe_all_here.itemservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemValidator {

    private final ItemRepository itemRepository;

    public boolean isNullItem(Long itemId) {
        Item item = itemRepository.findOneById(itemId);

        return CommonUtils.isNull(item);
    }
}
