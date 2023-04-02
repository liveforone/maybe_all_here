package maybe_all_here.itemservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.authentication.Role;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.repository.item.ItemRepository;
import maybe_all_here.itemservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ItemValidator {

    private final ItemRepository itemRepository;

    public boolean isNullItem(Long itemId) {
        Item item = itemRepository.findOneById(itemId);

        return CommonUtils.isNull(item);
    }

    public boolean isNotSeller(String auth) {
        return !Objects.equals(auth, Role.SELLER.getValue());
    }
}
