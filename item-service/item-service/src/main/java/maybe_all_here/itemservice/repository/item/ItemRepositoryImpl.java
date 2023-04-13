package maybe_all_here.itemservice.repository.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.domain.QItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory queryFactory;
    QItem item = QItem.item;

    private BooleanExpression ltItemId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return item.id.lt(lastId);
    }

    public List<Item> findItemHome(Long lastId, int pageSize) {
        return queryFactory.selectFrom(item)
                .where(ltItemId(lastId))
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
                        ltItemId(lastId)
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
                        ltItemId(lastId)
                )
                .orderBy(
                        item.good.desc(),
                        item.id.desc()
                )
                .limit(pageSize)
                .fetch();
    }

    public void deleteItemById(Long itemId) {
        queryFactory.delete(item)
                .where(item.id.eq(itemId))
                .execute();
    }
}
