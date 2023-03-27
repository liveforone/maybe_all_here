package may_all_here.shopservice.service;

import jakarta.persistence.EntityManager;
import may_all_here.shopservice.dto.shop.ShopRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ShopService shopService;

    @Test
    @Transactional
    void createShopTest() {
        //given
        String shopName = "test1";
        String address = "seoul, S.Korea";
        String tel = "07011111111";
        String email = "test1234@gmail.com";
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setShopName(shopName);
        shopRequest.setAddress(address);
        shopRequest.setTel(tel);
        shopRequest.setEmail(email);

        //when
        Long shopId = shopService.createShop(shopRequest);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopById(shopId).getShopName())
                .isEqualTo(shopName);
    }

    @Test
    void updateShopName() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void updateTel() {
    }
}