package may_all_here.shopservice.validator;

import lombok.RequiredArgsConstructor;
import may_all_here.shopservice.authentication.Role;
import may_all_here.shopservice.domain.Shop;
import may_all_here.shopservice.dto.shop.ShopRequest;
import may_all_here.shopservice.repository.ShopRepository;
import may_all_here.shopservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ShopValidator {

    private final ShopRepository shopRepository;

    public boolean isNotSeller(String auth) {
        return !Objects.equals(auth, Role.SELLER.getValue());
    }

    public boolean isDuplicateUser(String email) {
        Shop shop = shopRepository.findShopByEmail(email);

        return !CommonUtils.isNull(shop);
    }

    public boolean isNotExistShop(Long shopId) {
        Shop shop = shopRepository.findShopById(shopId);

        return CommonUtils.isNull(shop);
    }

    public boolean isNotOwner(String email) {
        Shop shop = shopRepository.findShopByEmail(email);

        return CommonUtils.isNull(shop);
    }
}
