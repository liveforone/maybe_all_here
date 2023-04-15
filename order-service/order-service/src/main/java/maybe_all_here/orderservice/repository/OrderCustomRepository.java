package maybe_all_here.orderservice.repository;

import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.order.OrderResponse;

import java.util.List;

public interface OrderCustomRepository {

    Orders findOneForProvideById(Long orderId);

    List<OrderResponse> findOrdersByEmail(String email, Long lastId, int pageSize);

    Orders findOneById(Long orderId);
}
