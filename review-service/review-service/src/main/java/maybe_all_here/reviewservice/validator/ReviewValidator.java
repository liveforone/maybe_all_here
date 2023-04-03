package maybe_all_here.reviewservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.dto.order.OrderProvideResponse;
import maybe_all_here.reviewservice.dto.order.OrderState;
import maybe_all_here.reviewservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    public boolean isCancelOrder(OrderProvideResponse order) {
        if (order.getOrderState() == OrderState.CANCEL) {
            return false;
        } else return !CommonUtils.isNull(order);
    }
}
