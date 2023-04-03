package maybe_all_here.reviewservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.dto.review.ReviewRequest;
import maybe_all_here.reviewservice.dto.review.ReviewResponse;
import maybe_all_here.reviewservice.kafka.ReviewProducer;
import maybe_all_here.reviewservice.repository.ReviewRepository;
import maybe_all_here.reviewservice.service.util.ReviewMapper;
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
        return ReviewMapper.entityToDtoList(
                reviewRepository.findReviewsByItemId(itemId, lastId, pageSize)
        );
    }

    @Transactional
    public void createReview(ReviewRequest reviewRequest, String email, Long itemId) {
        reviewRequest.setEmail(email);
        reviewRequest.setItemId(itemId);

//        reviewProducer.sendRecommendState(reviewRequest);
        reviewRepository.save(ReviewMapper.dtoToEntity(reviewRequest));
    }
}
