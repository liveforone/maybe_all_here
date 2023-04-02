package may_all_here.shopservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import may_all_here.shopservice.authentication.AuthenticationInfo;
import may_all_here.shopservice.controller.constant.ControllerLog;
import may_all_here.shopservice.controller.constant.ParamConstant;
import may_all_here.shopservice.controller.constant.ShopUrl;
import may_all_here.shopservice.controller.restResponse.RestResponse;
import may_all_here.shopservice.dto.shop.ShopRequest;
import may_all_here.shopservice.dto.shop.ShopResponse;
import may_all_here.shopservice.service.ShopService;
import may_all_here.shopservice.utility.CommonUtils;
import may_all_here.shopservice.validator.ShopValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;
    private final ShopValidator shopValidator;
    private final AuthenticationInfo authenticationInfo;

    @PostMapping(ShopUrl.CREATE_SHOP)
    public ResponseEntity<?> createShop(
            @RequestBody @Valid ShopRequest shopRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        if (shopValidator.isNotSeller(authenticationInfo.getAuth(request))) {
            return RestResponse.notSellerError();
        }

        String email = authenticationInfo.getEmail(request);
        if (shopValidator.isDuplicateUser(email)) {
            return RestResponse.duplicateUser();
        }

        Long shopId = shopService.createShop(shopRequest, email);
        log.info(ControllerLog.SHOP_CREATE_SUCCESS.getValue() + shopId);

        return RestResponse.createShopSuccess();
    }

    @GetMapping(ShopUrl.SHOP_DETAIL)
    public ResponseEntity<?> shopDetail(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId
    ) {
        ShopResponse shop = shopService.getShopById(shopId);

        if (CommonUtils.isNull(shop)) {
            return RestResponse.shopIsNull();
        }

        return ResponseEntity.ok(shop);
    }

    @GetMapping(ShopUrl.SHOP_SELLER_PAGE)
    public ResponseEntity<?> shopSellerPage(
            @PathVariable(ParamConstant.EMAIL) String email
    ) {
        ShopResponse shop = shopService.getShopByEmail(email);

        if (CommonUtils.isNull(shop)) {
            return RestResponse.shopIsNull();
        }

        return ResponseEntity.ok(shop);
    }

    @PatchMapping(ShopUrl.CHANGE_SHOP_NAME)
    public ResponseEntity<?> changeShopName(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            @RequestBody String shopName,
            HttpServletRequest request
    ) {
        if (shopValidator.isNotExistShop(shopId)) {
            return RestResponse.shopIsNull();
        }

        String email = authenticationInfo.getEmail(request);
        if (shopValidator.isNotOwner(email)) {
            return RestResponse.notOwner();
        }

        shopService.updateShopName(shopName, shopId);
        log.info(ControllerLog.UPDATE_SHOP_NAME_SUCCESS.getValue() + shopId);

        return RestResponse.updateShopNameSuccess();
    }

    @PatchMapping(ShopUrl.CHANGE_SHOP_ADDRESS)
    public ResponseEntity<?> changeShopAddress(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            @RequestBody String address,
            HttpServletRequest request
    ) {
        if (shopValidator.isNotExistShop(shopId)) {
            return RestResponse.shopIsNull();
        }

        String email = authenticationInfo.getEmail(request);
        if (shopValidator.isNotOwner(email)) {
            return RestResponse.notOwner();
        }

        shopService.updateAddress(address, shopId);
        log.info(ControllerLog.UPDATE_SHOP_ADDRESS_SUCCESS.getValue() + shopId);

        return RestResponse.updateAddressSuccess();
    }

    @PatchMapping(ShopUrl.CHANGE_SHOP_TEL)
    public ResponseEntity<?> changeShopTel(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            @RequestBody String tel,
            HttpServletRequest request
    ) {
        if (shopValidator.isNotExistShop(shopId)) {
            return RestResponse.shopIsNull();
        }

        String email = authenticationInfo.getEmail(request);
        if (shopValidator.isNotOwner(email)) {
            return RestResponse.notOwner();
        }

        shopService.updateTel(tel, shopId);
        log.info(ControllerLog.UPDATE_SHOP_TEL_SUCCESS.getValue() + shopId);

        return RestResponse.updateTelSuccess();
    }
}
