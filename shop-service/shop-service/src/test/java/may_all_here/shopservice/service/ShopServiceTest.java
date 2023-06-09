package may_all_here.shopservice.service;

import jakarta.persistence.EntityManager;
import may_all_here.shopservice.dto.shop.ShopRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ShopServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ShopService shopService;

    private Long createShop(
            String shopName, String address,
            String tel, String email
    ) {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setShopName(shopName);
        shopRequest.setAddress(address);
        shopRequest.setTel(tel);
        return shopService.createShop(shopRequest, email);
    }

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

        //when
        Long shopId = shopService.createShop(shopRequest, email);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopById(shopId).getShopName())
                .isEqualTo(shopName);
    }

    @Test
    @Transactional
    void updateShopNameTest() {
        //given
        String shopName = "test2";
        String address = "seoul, S.Korea";
        String tel = "07011111111";
        String email = "test1234@gmail.com";
        Long shopId = createShop(shopName, address, tel, email);
        em.flush();
        em.clear();

        //when
        String updatedShopName = "updated test2";
        shopService.updateShopName(updatedShopName, shopId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopById(shopId).getShopName())
                .isEqualTo(updatedShopName);
    }

    @Test
    @Transactional
    void updateAddressTest() {
        //given
        String shopName = "test3";
        String address = "seoul, S.Korea";
        String tel = "07011111111";
        String email = "test1234@gmail.com";
        Long shopId = createShop(shopName, address, tel, email);
        em.flush();
        em.clear();

        //when
        String updatedAddress = "pangyo, S.Korea";
        shopService.updateAddress(updatedAddress, shopId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopById(shopId).getAddress())
                .isEqualTo(updatedAddress);
    }

    @Test
    @Transactional
    void updateTelTest() {
        //given
        String shopName = "test4";
        String address = "seoul, S.Korea";
        String tel = "07011111111";
        String email = "test1234@gmail.com";
        Long shopId = createShop(shopName, address, tel, email);
        em.flush();
        em.clear();

        //when
        String updatedTel = "07012345678";
        shopService.updateTel(updatedTel, shopId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopById(shopId).getTel())
                .isEqualTo(updatedTel);
    }
}