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

    /*
    상품 페이지
    일반 상점 상품리스트
    셀러 상점 상품리스트
    검색(페이징)
    수정(제목, 내용, 가격, 재고)
    삭제(파일 -> 리뷰 -> 상품 순으로)
     */

    @GetMapping(ItemUrl.ITEM_HOME)
    public ResponseEntity<?> itemHome(
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
