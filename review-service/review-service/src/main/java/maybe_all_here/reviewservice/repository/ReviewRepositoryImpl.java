package maybe_all_here.reviewservice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.domain.QReview;
import maybe_all_here.reviewservice.domain.Review;
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

    public List<Review> findReviewsByItemId(Long itemId, Long lastId, int pageSize) {
        return queryFactory.selectFrom(review)
                .where(
                        review.itemId.eq(itemId),
                        ltReviewId(lastId)
                )
                .orderBy(review.id.desc())
                .limit(pageSize)
                .fetch();
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
