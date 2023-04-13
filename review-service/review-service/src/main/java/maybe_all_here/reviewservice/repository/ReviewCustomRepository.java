package maybe_all_here.reviewservice.repository;

import maybe_all_here.reviewservice.domain.Review;

import java.util.List;

public interface ReviewCustomRepository {

    Review findOneById(Long reviewId);

    Review findOneByOrderId(Long orderId);
    List<Review> findReviewsByItemId(Long itemId, Long lastId, int pageSize);

    void deleteOneById(Long reviewId);

    void deleteOneByOrderId(Long orderId);
    void deleteBulkByItemId(Long itemId);
}
