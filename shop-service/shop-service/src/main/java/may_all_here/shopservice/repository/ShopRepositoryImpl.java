package may_all_here.shopservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import may_all_here.shopservice.domain.QShop;
import may_all_here.shopservice.domain.Shop;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopCustomRepository {

    private final JPAQueryFactory queryFactory;
    QShop shop = QShop.shop;

    public Shop findShopByEmail(String email) {
        return queryFactory.selectFrom(shop)
                .where(shop.email.eq(email))
                .fetchOne();
    }

    public Shop findShopById(Long id) {
        return queryFactory.selectFrom(shop)
                .where(shop.id.eq(id))
                .fetchOne();
    }

    public void updateAddress(String address, Long shopId) {
        queryFactory.update(shop)
                .set(shop.address, address)
                .where(shop.id.eq(shopId))
                .execute();
    }

    public void updateTel(String tel, Long shopId) {
        queryFactory.update(shop)
                .set(shop.tel, tel)
                .where(shop.id.eq(shopId))
                .execute();
    }
}
