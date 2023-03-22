package may_all_here.shopservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
