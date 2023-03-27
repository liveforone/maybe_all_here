package may_all_here.shopservice.controller;

import lombok.RequiredArgsConstructor;
import may_all_here.shopservice.controller.constant.ParamConstant;
import may_all_here.shopservice.controller.constant.ShopProvideUrl;
import may_all_here.shopservice.dto.shop.SellerInfoResponse;
import may_all_here.shopservice.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShopProvideController {

    private final ShopService shopService;

    @PostMapping(ShopProvideUrl.GET_SELLER_INFO)
    public ResponseEntity<?> getSellerInfo(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId
    ) {
        SellerInfoResponse sellerInfo = shopService.getSellerInfo(shopId);

        return ResponseEntity.ok(sellerInfo);
    }
}
