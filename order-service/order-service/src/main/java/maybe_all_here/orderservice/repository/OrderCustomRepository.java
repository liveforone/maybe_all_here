package maybe_all_here.orderservice.repository;

import maybe_all_here.orderservice.domain.Orders;

import java.util.List;

public interface OrderCustomRepository {

    Orders findOneForProvideById(Long orderId);

    List<Orders> findOrdersByEmail(String email, Long lastId, int pageSize);

    Orders findOneById(Long orderId);

    void cancelOneById(Long orderId);
}
