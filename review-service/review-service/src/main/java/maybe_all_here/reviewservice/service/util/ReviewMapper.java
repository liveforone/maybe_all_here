package maybe_all_here.reviewservice.service.util;

import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.ReviewRequest;
import maybe_all_here.reviewservice.dto.review.ReviewResponse;

import java.util.List;
import java.util.stream.Collectors;

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

    public static ReviewResponse entityToDtoDetail(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .email(review.getEmail())
                .content(review.getContent())
                .recommend(review.getRecommend())
                .build();
    }

    public static List<ReviewResponse> entityToDtoList(List<Review> reviews) {
        return reviews
                .stream()
                .map(ReviewMapper::entityToDtoDetail)
                .collect(Collectors.toList());
    }
}
