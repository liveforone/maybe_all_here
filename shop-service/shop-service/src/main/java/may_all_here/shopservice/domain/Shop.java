package may_all_here.shopservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import may_all_here.shopservice.dto.shop.ShopRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String tel;

    @Column(unique = true, nullable = true, updatable = false)
    private String email;

    @Builder
    public Shop(Long id, String shopName, String address, String tel, String email) {
        this.id = id;
        this.shopName = shopName;
        this.address = address;
        this.tel = tel;
        this.email = email;
    }

    //==Domain Logic Space==//

    public void create(ShopRequest shopRequest, String email) {
        shopRequest.setEmail(email);
        buildShop(shopRequest);
    }

    private void buildShop(ShopRequest shopRequest) {
        this.id = shopRequest.getId();
        this.shopName = shopRequest.getShopName();
        this.address = shopRequest.getAddress();
        this.tel = shopRequest.getTel();
        this.email = shopRequest.getEmail();
    }

    public void updateShopName(String shopName) {
        this.shopName = shopName;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    //==End Domain Logic Space==//
}
