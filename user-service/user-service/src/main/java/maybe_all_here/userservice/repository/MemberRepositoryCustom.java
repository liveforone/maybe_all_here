package maybe_all_here.userservice.repository;


import maybe_all_here.userservice.domain.Member;

public interface MemberRepositoryCustom {

    Long findIdForValidation(String email);
    String findPasswordForValidation(String email);
    Member findByEmail(String email);

    void deleteByEmail(String email);
}
