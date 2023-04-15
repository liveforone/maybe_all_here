package maybe_all_here.reviewservice.service.util;

import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.ReviewResponse;
import maybe_all_here.reviewservice.utility.CommonUtils;

public class ReviewMapper {

    public static ReviewResponse entityToDtoDetail(Review review) {
        if (CommonUtils.isNull(review)) {
            return new ReviewResponse();
        }

        return ReviewResponse.builder()
                .id(review.getId())
                .email(review.getEmail())
                .orderId(review.getOrderId())
                .content(review.getContent())
                .recommend(review.getRecommend())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
