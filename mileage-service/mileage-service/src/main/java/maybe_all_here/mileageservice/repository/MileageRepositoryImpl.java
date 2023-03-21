package maybe_all_here.mileageservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.mileageservice.domain.Mileage;
import maybe_all_here.mileageservice.domain.QMileage;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MileageRepositoryImpl implements MileageCustomRepository {

    private final JPAQueryFactory queryFactory;
    QMileage mileage = QMileage.mileage;

    public Mileage getMileageByEmail(String email) {
        return queryFactory.selectFrom(mileage)
                .where(mileage.email.eq(email))
                .fetchOne();
    }
}
