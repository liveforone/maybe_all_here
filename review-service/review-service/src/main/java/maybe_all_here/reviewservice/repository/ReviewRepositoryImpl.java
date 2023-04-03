package maybe_all_here.reviewservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.domain.QReview;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;
    QReview review = QReview.review;

    public void deleteBulkByItemId(Long itemId) {
        queryFactory.delete(review)
                .where(review.itemId.eq(itemId))
                .execute();
    }
}
