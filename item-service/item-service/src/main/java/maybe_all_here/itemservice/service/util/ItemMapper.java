package maybe_all_here.itemservice.service.util;

import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.dto.item.ItemRequest;

public class ItemMapper {

    public static Item dtoToEntity(ItemRequest itemRequest) {
        return Item.builder()
                .id(itemRequest.getId())
                .shopId(itemRequest.getShopId())
                .title(itemRequest.getTitle())
                .content(itemRequest.getContent())
                .price(itemRequest.getPrice())
                .remaining(itemRequest.getRemaining())
                .build();
    }
}
