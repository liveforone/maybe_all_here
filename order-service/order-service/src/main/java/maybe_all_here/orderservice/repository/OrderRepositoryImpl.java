package maybe_all_here.orderservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.domain.QOrders;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory queryFactory;
    QOrders orders = QOrders.orders;

    public Orders findOneByEmailAndItemId(String email, Long itemId) {
        return queryFactory.selectFrom(orders)
                .where(
                        orders.email.eq(email),
                        orders.itemId.eq(itemId)
                )
                .fetchOne();
    }

    public Orders findOneById(Long orderId) {
        return queryFactory.selectFrom(orders)
                .where(orders.id.eq(orderId))
                .fetchOne();
    }
}
