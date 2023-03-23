package may_all_here.shopservice.service.util;

import may_all_here.shopservice.domain.Shop;
import may_all_here.shopservice.dto.shop.ShopRequest;

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
}
