package maybe_all_here.userservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.userservice.domain.Member;
import maybe_all_here.userservice.domain.QMember;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QMember member = QMember.member;

    public Member findByEmail(String email) {
        return queryFactory.selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

    public void updatePassword(String password, String email) {
        queryFactory.update(member)
                .set(member.password, password)
                .where(member.email.eq(email))
                .execute();
    }

    public void deleteByEmail(String email) {
        queryFactory.delete(member)
                .where(member.email.eq(email))
                .execute();
    }
}
