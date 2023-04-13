package maybe_all_here.orderservice.service.util;

import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderResponse;
import maybe_all_here.orderservice.utility.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static OrderResponse entityToDto(Orders orders) {
        if (CommonUtils.isNull(orders)) {
            return new OrderResponse();
        }

        return OrderResponse.builder()
                .id(orders.getId())
                .itemTitle(orders.getItemTitle())
                .orderQuantity(orders.getOrderQuantity())
                .totalPrice(orders.getTotalPrice())
                .discountedPrice(orders.getDiscountedPrice())
                .orderState(orders.getOrderState())
                .build();
    }

    public static List<OrderResponse> entityToDtoList(List<Orders> orders) {
        if (CommonUtils.isNull(orders)) {
            return new ArrayList<>();
        }

        return orders
                .stream()
                .map(OrderMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
