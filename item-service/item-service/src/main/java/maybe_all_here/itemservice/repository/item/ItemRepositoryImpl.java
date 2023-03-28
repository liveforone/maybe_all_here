package maybe_all_here.itemservice.repository.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.domain.QItem;
import maybe_all_here.itemservice.dto.item.ItemRemainingRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory queryFactory;
    private static final long RECOMMEND = 1;
    QItem item = QItem.item;

    private BooleanExpression ltBookId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return item.id.lt(lastId);
    }

    public List<Item> findItemHome(Long lastId, int pageSize) {
        return queryFactory.selectFrom(item)
                .where(ltBookId(lastId))
                .orderBy(
                        item.id.desc(),
                        item.good.desc()
                )
                .limit(pageSize)
                .fetch();
    }

    public Item findOneById(Long itemId) {
        return queryFactory.selectFrom(item)
                .where(item.id.eq(itemId))
                .fetchOne();
    }

    public List<Item> findItemsByShopId(Long shopId, Long lastId, int pageSize) {
        return queryFactory.selectFrom(item)
                .where(
                        item.shopId.eq(shopId),
                        ltBookId(lastId)
                )
                .orderBy(
                        item.good.desc(),
                        item.id.desc()
                )
                .limit(pageSize)
                .fetch();
    }

    public List<Item> searchItemsByKeyword(String keyword, Long lastId, int pageSize) {
        return queryFactory.selectFrom(item)
                .where(
                        item.title.contains(keyword),
                        ltBookId(lastId)
                )
                .orderBy(
                        item.good.desc(),
                        item.id.desc()
                )
                .limit(pageSize)
                .fetch();
    }

    public void editTitleById(String title, Long itemId) {
        queryFactory.update(item)
                .set(item.title, title)
                .where(item.id.eq(itemId))
                .execute();
    }

    public void editContentById(String content, Long itemId) {
        queryFactory.update(item)
                .set(item.content, content)
                .where(item.id.eq(itemId))
                .execute();
    }

    public void editPriceById(long price, Long itemId) {
        queryFactory.update(item)
                .set(item.price, price)
                .where(item.id.eq(itemId))
                .execute();
    }

    public void editRemainingById(long remaining, Long itemId) {
        queryFactory.update(item)
                .set(item.remaining, remaining)
                .where(item.id.eq(itemId))
                .execute();
    }

    public void deleteItemById(Long itemId) {
        queryFactory.delete(item)
                .where(item.id.eq(itemId))
                .execute();
    }

    public void decreaseRemaining(ItemRemainingRequest itemRemainingRequest) {
        queryFactory.update(item)
                .set(
                        item.remaining,
                        item.remaining.add(-itemRemainingRequest.getOrderQuantity())
                )
                .where(item.id.eq(itemRemainingRequest.getItemId()))
                .execute();
    }

    public void increaseGood(Long itemId) {
        queryFactory.update(item)
                .set(item.good, item.good.add(RECOMMEND))
                .where(item.id.eq(itemId))
                .execute();
    }

    public void increaseBad(Long itemId) {
        queryFactory.update(item)
                .set(item.bad, item.bad.add(RECOMMEND))
                .where(item.id.eq(itemId))
                .execute();
    }
}
