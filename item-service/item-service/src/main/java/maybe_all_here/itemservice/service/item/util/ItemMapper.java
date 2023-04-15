package maybe_all_here.itemservice.service.item.util;

import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.dto.item.ItemDetailResponse;
import maybe_all_here.itemservice.dto.item.ItemProvideResponse;
import maybe_all_here.itemservice.dto.item.ItemResponse;
import maybe_all_here.itemservice.utility.CommonUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public static ItemResponse entityToDto(Item item) {
        if (CommonUtils.isNull(item)) {
            return new ItemResponse();
        } else {
            return ItemResponse.builder()
                    .id(item.getId())
                    .shopId(item.getShopId())
                    .title(item.getTitle())
                    .content(item.getContent())
                    .price(item.getPrice())
                    .remaining(item.getRemaining())
                    .good(item.getGood())
                    .bad(item.getBad())
                    .build();
        }
    }

    public static List<ItemResponse> entityToDtoList(List<Item> items) {
        return items
                .stream()
                .map(ItemMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static ItemDetailResponse entityToDtoDetail(Item item, List<String> saveFileNames) {
        if (CommonUtils.isNull(item)) {
            return new ItemDetailResponse();
        } else {
            return ItemDetailResponse.builder()
                    .id(item.getId())
                    .shopId(item.getShopId())
                    .title(item.getTitle())
                    .content(item.getContent())
                    .price(item.getPrice())
                    .remaining(item.getRemaining())
                    .good(item.getGood())
                    .bad(item.getBad())
                    .saveFileName(saveFileNames)
                    .build();
        }
    }

    public static ItemProvideResponse entityToProvideDto(Item item) {
        return ItemProvideResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .itemPrice(item.getPrice())
                .remaining(item.getRemaining())
                .build();
    }
}
