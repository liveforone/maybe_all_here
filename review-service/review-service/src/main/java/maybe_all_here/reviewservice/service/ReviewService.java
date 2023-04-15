package maybe_all_here.reviewservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.async.AsyncConstant;
import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.ReviewEditRequest;
import maybe_all_here.reviewservice.dto.review.ReviewRequest;
import maybe_all_here.reviewservice.dto.review.ReviewResponse;
import maybe_all_here.reviewservice.kafka.ReviewProducer;
import maybe_all_here.reviewservice.repository.ReviewRepository;
import maybe_all_here.reviewservice.service.util.ReviewMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewProducer reviewProducer;

    public ReviewResponse getReviewById(Long reviewId) {
        return ReviewMapper.entityToDtoDetail(
                reviewRepository.findOneById(reviewId)
        );
    }

    public List<ReviewResponse> getReviewsByItemId(Long itemId, Long lastId, int pageSize) {
        return reviewRepository.findReviewsByItemId(itemId, lastId, pageSize);
    }

    @Transactional
    public Long createReview(
            ReviewRequest reviewRequest, String email, Long itemId
    ) {
        Review review = Review.builder().build();
        review.create(reviewRequest, email, itemId);
        reviewProducer.sendRecommendState(review);
        return reviewRepository.save(review).getId();
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void editReviewById(ReviewEditRequest reviewEditRequest, Long reviewId) {
        Review review = reviewRepository.findOneById(reviewId);
        review.updateContent(reviewEditRequest.getContent());
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void deleteReviewById(Long reviewId) {
        reviewRepository.deleteOneById(reviewId);
    }
}
