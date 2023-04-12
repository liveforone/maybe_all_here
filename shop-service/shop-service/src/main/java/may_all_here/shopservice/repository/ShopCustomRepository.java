package may_all_here.shopservice.repository;

import may_all_here.shopservice.domain.Shop;

public interface ShopCustomRepository {

    Shop findShopByEmail(String email);

    Shop findShopById(Long id);

    void updateAddress(String address, Long shopId);

    void updateTel(String tel, Long shopId);
}
