package maybe_all_here.orderservice.repository;

import maybe_all_here.orderservice.domain.Orders;

public interface OrderCustomRepository {

    Orders findOneByEmailAndItemId(String email, Long itemId);
}
