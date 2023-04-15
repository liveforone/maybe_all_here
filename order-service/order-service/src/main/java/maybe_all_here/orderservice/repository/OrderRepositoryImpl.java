package maybe_all_here.orderservice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.domain.QOrders;
import maybe_all_here.orderservice.dto.order.OrderResponse;
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

    public Orders findOneForProvideById(Long orderId) {
        return queryFactory.selectFrom(orders)
                .where(orders.id.eq(orderId))
                .fetchOne();
    }

    public List<OrderResponse> findOrdersByEmail(String email, Long lastId, int pageSize) {
        return queryFactory.select(Projections.constructor(OrderResponse.class,
                orders.id,
                orders.itemTitle,
                orders.orderQuantity,
                orders.totalPrice,
                orders.discountedPrice,
                orders.orderState,
                orders.createdDate
                ))
                .from(orders)
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
}
