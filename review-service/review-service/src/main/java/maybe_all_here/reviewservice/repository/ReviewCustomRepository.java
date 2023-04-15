package maybe_all_here.reviewservice.repository;

import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.ReviewResponse;

import java.util.List;

public interface ReviewCustomRepository {

    Review findOneById(Long reviewId);

    Review findOneByOrderId(Long orderId);
    List<ReviewResponse> findReviewsByItemId(Long itemId, Long lastId, int pageSize);

    void deleteOneById(Long reviewId);

    void deleteOneByOrderId(Long orderId);
    void deleteBulkByItemId(Long itemId);
}
