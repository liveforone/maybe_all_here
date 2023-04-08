package maybe_all_here.orderservice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.domain.OrderState;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.domain.QOrders;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory queryFactory;
    QOrders orders = QOrders.orders;

    private BooleanExpression ltOrderId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return orders.id.lt(lastId);
    }

    public Orders findOneByEmailAndItemId(String email, Long itemId) {
        return queryFactory.selectFrom(orders)
                .where(
                        orders.email.eq(email),
                        orders.itemId.eq(itemId)
                )
                .fetchOne();
    }

    public List<Orders> findOrdersByEmail(String email, Long lastId, int pageSize) {
        return queryFactory.selectFrom(orders)
                .where(
                        orders.email.eq(email),
                        ltOrderId(lastId)
                )
                .orderBy(orders.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public Orders findOneById(Long orderId) {
        return queryFactory.selectFrom(orders)
                .where(orders.id.eq(orderId))
                .fetchOne();
    }

    public void cancelOneById(Long orderId) {
        queryFactory.update(orders)
                .set(orders.orderState, OrderState.CANCEL)
                .where(orders.id.eq(orderId))
                .execute();
    }
}
