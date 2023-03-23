package may_all_here.shopservice.validator;

import lombok.RequiredArgsConstructor;
import may_all_here.shopservice.domain.Shop;
import may_all_here.shopservice.dto.member.MemberResponse;
import may_all_here.shopservice.dto.member.Role;
import may_all_here.shopservice.dto.shop.ShopRequest;
import may_all_here.shopservice.feignClient.MemberFeignService;
import may_all_here.shopservice.repository.ShopRepository;
import may_all_here.shopservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopValidator {

    private final ShopRepository shopRepository;
    private final MemberFeignService memberFeignService;

    public boolean isNotSeller(ShopRequest shopRequest) {
        MemberResponse memberInfo = memberFeignService.getMemberInfo(shopRequest.getEmail());

        return memberInfo.getAuth() != Role.SELLER;
    }

    public boolean isDuplicateUser(ShopRequest shopRequest) {
        Shop shop = shopRepository.findShopByEmail(shopRequest.getEmail());

        return !CommonUtils.isNull(shop);
    }
}
