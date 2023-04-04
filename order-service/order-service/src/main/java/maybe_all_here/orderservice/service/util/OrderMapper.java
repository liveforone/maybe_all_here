package maybe_all_here.orderservice.service.util;

import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;

public class OrderMapper {

    public static OrderProvideResponse entityToProvideDto(Orders orders) {
        return OrderProvideResponse.builder()
                .id(orders.getId())
                .itemId(orders.getItemId())
                .orderState(orders.getOrderState())
                .build();
    }
}
