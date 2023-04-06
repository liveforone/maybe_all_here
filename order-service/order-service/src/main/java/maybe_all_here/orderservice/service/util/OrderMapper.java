package maybe_all_here.orderservice.service.util;

import maybe_all_here.orderservice.domain.OrderState;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.utility.CommonUtils;
import maybe_all_here.orderservice.utility.PriceCalculator;

public class OrderMapper {

    public static Orders dtoToEntity(OrderRequest orderRequest) {
        return Orders.builder()
                .itemTitle(orderRequest.getItemTitle())
                .orderQuantity(orderRequest.getOrderQuantity())
                .totalPrice(orderRequest.getTotalPrice())
                .discountedPrice(orderRequest.getSpentMileage())
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
}
