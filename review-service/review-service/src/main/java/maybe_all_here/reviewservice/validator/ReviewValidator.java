package maybe_all_here.reviewservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.order.OrderProvideResponse;
import maybe_all_here.reviewservice.dto.order.OrderState;
import maybe_all_here.reviewservice.repository.ReviewRepository;
import maybe_all_here.reviewservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final ReviewRepository reviewRepository;

    public boolean isNullReview(Long reviewId) {
        Review review = reviewRepository.findOneById(reviewId);

        return CommonUtils.isNull(review);
    }

    public boolean isCancelOrder(OrderProvideResponse order) {
        return order.getOrderState() == OrderState.CANCEL;
    }

    public boolean isNotOwner(Long reviewId, String email) {
        Review review = reviewRepository.findOneById(reviewId);

        return !Objects.equals(review.getEmail(), email);
    }

    public boolean isDuplicateReview(Long orderId) {
        Review review = reviewRepository.findOneByOrderId(orderId);

        return !CommonUtils.isNull(review);
    }
}
