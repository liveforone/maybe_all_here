package maybe_all_here.reviewservice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.domain.QReview;
import maybe_all_here.reviewservice.domain.Review;
import maybe_all_here.reviewservice.dto.review.ReviewResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;
    QReview review = QReview.review;

    public Review findOneById(Long reviewId) {
        return queryFactory.selectFrom(review)
                .where(review.id.eq(reviewId))
                .fetchOne();
    }

    public Review findOneByOrderId(Long orderId) {
        return queryFactory.selectFrom(review)
                .where(review.orderId.eq(orderId))
                .fetchOne();
    }

    public List<ReviewResponse> findReviewsByItemId(Long itemId, Long lastId, int pageSize) {
        return queryFactory
                .select(Projections.constructor(ReviewResponse.class,
                        review.id,
                        review.email,
                        review.orderId,
                        review.content,
                        review.recommend,
                        review.createdDate)
                )
                .from(review)
                .where(
                        review.itemId.eq(itemId),
                        ltReviewId(lastId)
                )
                .orderBy(review.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public void deleteOneById(Long reviewId) {
        queryFactory.delete(review)
                .where(review.id.eq(reviewId))
                .execute();
    }

    public void deleteOneByOrderId(Long orderId) {
        queryFactory.delete(review)
                .where(review.orderId.eq(orderId))
                .execute();
    }

    public void deleteBulkByItemId(Long itemId) {
        queryFactory.delete(review)
                .where(review.itemId.eq(itemId))
                .execute();
    }

    private BooleanExpression ltReviewId(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return review.id.lt(lastId);
    }
}
