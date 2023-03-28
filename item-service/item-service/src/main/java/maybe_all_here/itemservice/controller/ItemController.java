package maybe_all_here.itemservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.itemservice.controller.constant.ControllerLog;
import maybe_all_here.itemservice.controller.constant.ItemUrl;
import maybe_all_here.itemservice.controller.constant.ParamConstant;
import maybe_all_here.itemservice.controller.restResponse.RestResponse;
import maybe_all_here.itemservice.dto.item.ItemRequest;
import maybe_all_here.itemservice.dto.item.ItemResponse;
import maybe_all_here.itemservice.kafka.ItemProducer;
import maybe_all_here.itemservice.service.ItemService;
import maybe_all_here.itemservice.validator.ItemValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemProducer itemProducer;
    private final ItemValidator itemValidator;

    @GetMapping(ItemUrl.ITEM_HOME)
    public ResponseEntity<List<ItemResponse>> itemHome(
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ItemResponse> itemHome = itemService.getItemHome(lastId, pageSize);

        return ResponseEntity.ok(itemHome);
    }

    @GetMapping(ItemUrl.ITEM_DETAIL)
    public ResponseEntity<?> itemDetail(
            @PathVariable(ParamConstant.ITEM_ID) Long itemId
    ) {
        if (itemValidator.isNullItem(itemId)) {
            return RestResponse.itemIsNull();
        }

        ItemResponse item = itemService.getItemById(itemId);
        return ResponseEntity.ok(item);
    }

    @GetMapping(ItemUrl.SHOP_ITEMS_LIST)
    public ResponseEntity<List<ItemResponse>> shopItemsList(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ItemResponse> items = itemService.getItemsByShopId(shopId, lastId, pageSize);

        return ResponseEntity.ok(items);
    }

    @GetMapping(ItemUrl.SELLER_ITEMS_LIST)
    public ResponseEntity<List<ItemResponse>> sellerItemsList(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ItemResponse> items = itemService.getItemsByShopId(shopId, lastId, pageSize);

        return ResponseEntity.ok(items);
    }

    @GetMapping(ItemUrl.SEARCH_ITEMS_LIST)
    public ResponseEntity<List<ItemResponse>> searchItems(
            @RequestParam(name = ParamConstant.KEYWORD) String keyword,
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ItemResponse> items = itemService.searchItemsByKeyword(keyword, lastId, pageSize);

        return ResponseEntity.ok(items);
    }

    @PatchMapping(ItemUrl.EDIT_TITLE)
    public ResponseEntity<?> editTitle(
            @PathVariable(ParamConstant.ITEM_ID) Long itemId,
            @RequestBody String title
    ) {
        if (itemValidator.isNullItem(itemId)) {
            return RestResponse.itemIsNull();
        }

        itemService.editTitleById(title, itemId);
        log.info(ControllerLog.EDIT_TITLE_SUCCESS.getValue() + itemId);

        return RestResponse.editTitleSuccess();
    }

    @PatchMapping(ItemUrl.EDIT_CONTENT)
    public ResponseEntity<?> editContent(
            @PathVariable(ParamConstant.ITEM_ID) Long itemId,
            @RequestBody String content
    ) {
        if (itemValidator.isNullItem(itemId)) {
            return RestResponse.itemIsNull();
        }

        itemService.editContentById(content, itemId);
        log.info(ControllerLog.EDIT_CONTENT_SUCCESS.getValue() + itemId);

        return RestResponse.editContentSuccess();
    }

    /*
    수정(내용, 가격, 재고)
    삭제(파일 -> 리뷰 -> 상품 순으로)
     */

    @PostMapping(ItemUrl.CREATE_ITEM)
    public ResponseEntity<?> createItem(
            @RequestPart @Valid ItemRequest itemRequest,
            BindingResult bindingResult,
            @RequestPart List<MultipartFile> uploadFile,
            @PathVariable(ParamConstant.SHOP_ID) Long shopId
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        Long itemId = itemService.createItem(itemRequest, shopId);
        itemProducer.saveFile(uploadFile, itemId);
        log.info(ControllerLog.CREATE_ITEM_SUCCESS.getValue() + itemId);

        return RestResponse.createItemSuccess();
    }
}
