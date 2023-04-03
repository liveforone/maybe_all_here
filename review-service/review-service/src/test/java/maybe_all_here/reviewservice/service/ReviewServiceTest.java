package maybe_all_here.reviewservice.service;

import jakarta.persistence.EntityManager;
import maybe_all_here.reviewservice.dto.review.ReviewEditRequest;
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

    private Long createReview(Long itemId, String email, String content, String recommend) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setContent(content);
        reviewRequest.setRecommend(recommend);
        return reviewService.createReview(reviewRequest, email, itemId);
    }

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
    @Transactional
    void editReviewByIdTest() {
        //given
        Long itemId = 1L;
        String email = "aa1234@gmail.com";
        String content = "test_content";
        String recommend = "true";
        Long reviewId = createReview(itemId, email, content, recommend);

        //when
        String updatedContent = "updated content";
        ReviewEditRequest request = new ReviewEditRequest();
        request.setContent(updatedContent);
        reviewService.editReviewById(request, reviewId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(reviewService.getReviewById(reviewId).getContent())
                .isEqualTo(updatedContent);
    }

    @Test
    @Transactional
    void deleteReviewById() {
        //given
        Long itemId = 1L;
        String email = "aa1234@gmail.com";
        String content = "test_content";
        String recommend = "true";
        Long reviewId = createReview(itemId, email, content, recommend);

        //when
        reviewService.deleteReviewById(reviewId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(reviewService.getReviewById(reviewId).getId())
                .isNull();
    }
}