package maybe_all_here.reviewservice.service.util;

import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.ReviewResponse;
import maybe_all_here.reviewservice.utility.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<ReviewResponse> entityToDtoList(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return new ArrayList<>();
        }

        return reviews
                .stream()
                .map(ReviewMapper::entityToDtoDetail)
                .collect(Collectors.toList());
    }
}
