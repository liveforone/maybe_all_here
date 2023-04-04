package maybe_all_here.orderservice.service.util;

import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.utility.CommonUtils;

public class OrderMapper {

    public static OrderProvideResponse entityToProvideDto(Orders orders) {
        if (CommonUtils.isNull(orders)) {
            return new OrderProvideResponse();
        }

        return OrderProvideResponse.builder()
                .id(orders.getId())
                .itemId(orders.getItemId())
                .orderState(orders.getOrderState())
                .build();
    }
}
