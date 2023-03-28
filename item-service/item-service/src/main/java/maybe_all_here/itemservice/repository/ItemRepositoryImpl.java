package maybe_all_here.itemservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.domain.QItem;
import maybe_all_here.itemservice.dto.ItemRemainingRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory queryFactory;
    QItem item = QItem.item;

    public void decreaseRemaining(ItemRemainingRequest itemRemainingRequest) {
        queryFactory.update(item)
                .set(
                        item.remaining,
                        item.remaining.add(-itemRemainingRequest.getOrderQuantity())
                )
                .where(item.id.eq(itemRemainingRequest.getItemId()))
                .execute();
    }
}
