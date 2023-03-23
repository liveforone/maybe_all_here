package may_all_here.shopservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import may_all_here.shopservice.controller.constant.ControllerLog;
import may_all_here.shopservice.controller.constant.ShopUrl;
import may_all_here.shopservice.controller.restResponse.RestResponse;
import may_all_here.shopservice.dto.shop.ShopRequest;
import may_all_here.shopservice.service.ShopService;
import may_all_here.shopservice.validator.ShopValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;
    private final ShopValidator shopValidator;

    @PostMapping(ShopUrl.CREATE_SHOP)
    public ResponseEntity<?> createShop(
            @RequestBody @Valid ShopRequest shopRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        if (shopValidator.isNotSeller(shopRequest)) {
            return RestResponse.notSellerError();
        }

        if (shopValidator.isDuplicateUser(shopRequest)) {
            return RestResponse.duplicateUser();
        }

        Long shopId = shopService.createShop(shopRequest);
        log.info(ControllerLog.SHOP_CREATE_SUCCESS.getValue() + shopId);

        return RestResponse.createShopSuccess();
    }
}
