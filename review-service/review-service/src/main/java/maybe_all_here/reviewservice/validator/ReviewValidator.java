package maybe_all_here.reviewservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.dto.order.OrderProvideResponse;
import maybe_all_here.reviewservice.dto.order.OrderState;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    public boolean isCancelOrder(OrderProvideResponse order) {
        return order.getOrderState() == OrderState.CANCEL;
    }
}
