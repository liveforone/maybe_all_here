package may_all_here.shopservice.service;

import lombok.RequiredArgsConstructor;
import may_all_here.shopservice.async.AsyncConstant;
import may_all_here.shopservice.domain.Shop;
import may_all_here.shopservice.dto.shop.ShopRequest;
import may_all_here.shopservice.dto.shop.ShopResponse;
import may_all_here.shopservice.repository.ShopRepository;
import may_all_here.shopservice.service.util.ShopMapper;
import may_all_here.shopservice.utility.CommonUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopResponse getShopById(Long shopId) {
        Shop shop = shopRepository.findShopById(shopId);

        if (CommonUtils.isNull(shop)) {
            return new ShopResponse();
        }

        return ShopMapper.entityToDtoDetail(shop);
    }

    public ShopResponse getShopByEmail(String email) {
        Shop shop = shopRepository.findShopByEmail(email);

        if (CommonUtils.isNull(shop)) {
            return new ShopResponse();
        }

        return ShopMapper.entityToDtoDetail(shop);
    }

    @Transactional
    public Long createShop(ShopRequest shopRequest, String email) {
        Shop shop = Shop.builder().build();
        shop.create(shopRequest, email);
        return shopRepository.save(shop).getId();
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateShopName(String shopName, Long shopId) {
        Shop shop = shopRepository.findShopById(shopId);
        shop.updateShopName(shopName);

        shopRepository.save(shop);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateAddress(String address, Long shopId) {
        Shop shop = shopRepository.findShopById(shopId);
        shop.updateAddress(address);

        shopRepository.save(shop);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateTel(String tel, Long shopId) {
        shopRepository.updateTel(tel, shopId);
    }
}
