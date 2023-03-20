package maybe_all_here.userservice.repository;


import maybe_all_here.userservice.domain.Member;

public interface MemberRepositoryCustom {

    Member findByEmail(String email);

    void updateEmail(String oldEmail, String newEmail);

    void updatePassword(String password, String email);

    void deleteByEmail(String email);
}
