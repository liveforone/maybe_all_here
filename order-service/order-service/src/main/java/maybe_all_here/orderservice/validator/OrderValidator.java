package maybe_all_here.orderservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private static final int SOLD_OUT = 0;

    public boolean isSoldOut(ItemProvideResponse item) {
        return item.getRemaining() <= SOLD_OUT;
    }

    public boolean isOverRemaining(OrderRequest orderRequest, ItemProvideResponse item) {
        return orderRequest.getOrderQuantity() > item.getRemaining();
    }
}
