package maybe_all_here.itemservice.service.item.util;

import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.domain.UploadFile;
import maybe_all_here.itemservice.dto.item.ItemDetailResponse;
import maybe_all_here.itemservice.dto.item.ItemProvideResponse;
import maybe_all_here.itemservice.dto.item.ItemRequest;
import maybe_all_here.itemservice.dto.item.ItemResponse;
import maybe_all_here.itemservice.utility.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static ItemDetailResponse entityToDtoDetail(Item item, List<UploadFile> files) {
        if (CommonUtils.isNull(item)) {
            return new ItemDetailResponse();
        } else {
            List<String> saveFileName = new ArrayList<>();
            for (UploadFile file : files) {
                saveFileName.add(file.getSaveFileName());
            }

            return ItemDetailResponse.builder()
                    .id(item.getId())
                    .shopId(item.getShopId())
                    .title(item.getTitle())
                    .content(item.getContent())
                    .price(item.getPrice())
                    .remaining(item.getRemaining())
                    .good(item.getGood())
                    .bad(item.getBad())
                    .saveFileName(saveFileName)
                    .build();
        }
    }

    public static ItemProvideResponse entityToProvideDto(Item item) {
        return ItemProvideResponse.builder()
                .title(item.getTitle())
                .itemPrice(item.getPrice())
                .remaining(item.getRemaining())
                .build();
    }
}
