package maybe_all_here.orderservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.mileage.MileageResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final OrderRepository orderRepository;

    private static final int SOLD_OUT = 0;

    public boolean isSoldOut(ItemProvideResponse item) {
        return item.getRemaining() <= SOLD_OUT;
    }

    public boolean isOverRemaining(OrderRequest orderRequest, ItemProvideResponse item) {
        return orderRequest.getOrderQuantity() > item.getRemaining();
    }

    public boolean isOverMileage(OrderRequest orderRequest, MileageResponse mileage) {
        return orderRequest.getSpentMileage() > mileage.getMileagePoint();
    }

    public boolean isOverCancelLimitDate(Long orderId) {
        Orders orders = orderRepository.findOneById(orderId);
        LocalDate createdDate = orders.getCreatedDate();
        int orderDate = createdDate.getDayOfYear();

        int nowYear = LocalDate.now().getYear();
        int nowDate = LocalDate.now().getDayOfYear();

        int cancelLimitDate = switch (orderDate) {
            case 359 -> LocalDate.of(nowYear, 1, 1).getDayOfYear();
            case 360 -> LocalDate.of(nowYear, 1, 2).getDayOfYear();
            case 361 -> LocalDate.of(nowYear, 1, 3).getDayOfYear();
            case 362 -> LocalDate.of(nowYear, 1, 4).getDayOfYear();
            case 363 -> LocalDate.of(nowYear, 1, 5).getDayOfYear();
            case 364 -> LocalDate.of(nowYear, 1, 6).getDayOfYear();
            case 365 -> LocalDate.of(nowYear, 1, 7).getDayOfYear();
            default -> orderDate + 7;
        };

        return nowDate > cancelLimitDate;
    }
}
