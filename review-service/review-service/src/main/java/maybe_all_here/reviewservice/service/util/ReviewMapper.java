package maybe_all_here.reviewservice.service.util;

import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.ReviewRequest;

public class ReviewMapper {

    public static Review dtoToEntity(ReviewRequest reviewRequest) {
        return Review.builder()
                .id(reviewRequest.getId())
                .itemId(reviewRequest.getItemId())
                .email(reviewRequest.getEmail())
                .content(reviewRequest.getContent())
                .recommend(reviewRequest.getRecommend())
                .build();
    }
}
