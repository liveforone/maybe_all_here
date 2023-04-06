package maybe_all_here.itemservice.controller;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.controller.constant.ItemProvideUrl;
import maybe_all_here.itemservice.controller.constant.ParamConstant;
import maybe_all_here.itemservice.dto.item.ItemProvideResponse;
import maybe_all_here.itemservice.service.item.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemProvideController {

    private final ItemService itemService;

    @PostMapping(ItemProvideUrl.ITEM_PROVIDE_DETAIL)
    public ResponseEntity<?> itemProvideDetail(
            @PathVariable(ParamConstant.ITEM_ID) Long itemId
    ) {
        ItemProvideResponse item = itemService.getItemProvideById(itemId);

        return ResponseEntity.ok(item);
    }
}
