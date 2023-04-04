package maybe_all_here.orderservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.domain.QOrders;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl {

    private final JPAQueryFactory queryFactory;
    QOrders orders = QOrders.orders;


}
