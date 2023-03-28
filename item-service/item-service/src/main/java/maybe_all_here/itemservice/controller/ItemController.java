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

    /*
    상품 페이지
    셀러 상품 페이지
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
