package maybe_all_here.reviewservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.order.OrderProvideResponse;
import maybe_all_here.reviewservice.dto.order.OrderState;
import maybe_all_here.reviewservice.repository.ReviewRepository;
import maybe_all_here.reviewservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final ReviewRepository reviewRepository;

    public boolean isNullReview(Long reviewId) {
        Review review = reviewRepository.findOneById(reviewId);

        return CommonUtils.isNull(review);
    }

    public boolean isCancelOrder(OrderProvideResponse order) {
        if (order.getOrderState() == OrderState.CANCEL) {
            return false;
        } else return !CommonUtils.isNull(order);
    }
}
