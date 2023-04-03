package maybe_all_here.reviewservice.service;

import jakarta.persistence.EntityManager;
import maybe_all_here.reviewservice.dto.review.ReviewRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void createReviewTest() {
        //given
        Long itemId = 1L;
        String email = "aa1234@gmail.com";
        String content = "test_content";
        String recommend = "true";
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setContent(content);
        reviewRequest.setRecommend(recommend);

        //when
        Long reviewId = reviewService.createReview(reviewRequest, email, itemId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(reviewService.getReviewById(reviewId).getEmail())
                .isEqualTo(email);
    }

    @Test
    void editReviewById() {
    }

    @Test
    void deleteReviewById() {
    }
}