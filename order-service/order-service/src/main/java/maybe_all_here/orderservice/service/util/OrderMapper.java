package maybe_all_here.orderservice.service.util;

import maybe_all_here.orderservice.domain.OrderState;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.dto.order.OrderResponse;
import maybe_all_here.orderservice.utility.CommonUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Orders dtoToEntity(OrderRequest orderRequest) {
        return Orders.builder()
                .itemTitle(orderRequest.getItemTitle())
                .orderQuantity(orderRequest.getOrderQuantity())
                .totalPrice(orderRequest.getTotalPrice())
                .discountedPrice(orderRequest.getDiscountedPrice())
                .email(orderRequest.getEmail())
                .itemId(orderRequest.getItemId())
                .orderState(orderRequest.getOrderState())
                .build();
    }

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

    public static OrderRequest dtoToCalculatedDto(
            OrderRequest orderRequest, ItemProvideResponse item, String email
    ) {
        long totalPrice = PriceCalculator.calculateTotalPrice(
                item.getItemPrice(),
                orderRequest.getOrderQuantity()
        );

        long discountedPrice = PriceCalculator.calculateDiscountedPrice(
                totalPrice, orderRequest.getSpentMileage()
        );

        return OrderRequest.builder()
                .itemTitle(item.getTitle())
                .orderQuantity(orderRequest.getOrderQuantity())
                .totalPrice(totalPrice)
                .discountedPrice(discountedPrice)
                .spentMileage(orderRequest.getSpentMileage())
                .email(email)
                .itemId(item.getId())
                .orderState(OrderState.ORDER)
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
        return orders
                .stream()
                .map(OrderMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
