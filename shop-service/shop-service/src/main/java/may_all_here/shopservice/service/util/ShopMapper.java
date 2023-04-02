package may_all_here.shopservice.service.util;

import may_all_here.shopservice.domain.Shop;
import may_all_here.shopservice.dto.shop.ShopRequest;
import may_all_here.shopservice.dto.shop.ShopResponse;

public class ShopMapper {

    public static Shop dtoToEntity(ShopRequest shopRequest) {
        return Shop.builder()
                .id(shopRequest.getId())
                .shopName(shopRequest.getShopName())
                .address(shopRequest.getAddress())
                .tel(shopRequest.getTel())
                .email(shopRequest.getEmail())
                .build();
    }

    public static ShopResponse entityToDtoDetail(Shop shop) {
        return ShopResponse.builder()
                .shopName(shop.getShopName())
                .address(shop.getAddress())
                .tel(shop.getTel())
                .build();
    }
}
