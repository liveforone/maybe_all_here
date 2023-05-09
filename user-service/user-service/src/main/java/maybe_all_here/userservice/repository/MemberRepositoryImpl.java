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

    public Long findIdForValidation(String username) {
        return queryFactory
                .select(member.id)
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();
    }

    public String findPasswordForValidation(String username) {
        return queryFactory
                .select(member.password)
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();
    }

    public Member findByEmail(String email) {
        return queryFactory.selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

    public void deleteByEmail(String email) {
        queryFactory.delete(member)
                .where(member.email.eq(email))
                .execute();
    }
}
