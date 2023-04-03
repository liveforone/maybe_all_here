package maybe_all_here.reviewservice.repository;

import maybe_all_here.reviewservice.domain.Review;

import java.util.List;

public interface ReviewCustomRepository {

    Review findOneById(Long reviewId);
    List<Review> findReviewsByItemId(Long itemId, Long lastId, int pageSize);
    void deleteBulkByItemId(Long itemId);
}
