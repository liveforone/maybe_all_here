package maybe_all_here.userservice.service;

import jakarta.persistence.EntityManager;
import maybe_all_here.userservice.domain.Role;
import maybe_all_here.userservice.dto.changeInfo.ChangeEmailRequest;
import maybe_all_here.userservice.dto.signupAndLogin.MemberSignupRequest;
import maybe_all_here.userservice.validator.MemberValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberValidator memberValidator;

    @Autowired
    EntityManager em;

    void createMember(String email, String password) {
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest();
        memberSignupRequest.setEmail(email);
        memberSignupRequest.setPassword(password);
        memberSignupRequest.setRealName("test_member");
        memberService.signup(memberSignupRequest);
        em.flush();
        em.clear();
    }

    @Test
    @Transactional
    void signupSellerTest() {
        //given
        String email = "seller11@gmail.com";
        String password = "1111";
        String realName = "test_seller";
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest();
        memberSignupRequest.setEmail(email);
        memberSignupRequest.setPassword(password);
        memberSignupRequest.setRealName(realName);

        //when
        memberService.signupSeller(memberSignupRequest);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(memberService.getMemberByEmail(email).getAuth())
                .isEqualTo(Role.SELLER);
    }

    @Test
    @Transactional
    void updateEmailTest() {
        //given
        String email = "aa1111@gmail.com";
        String password = "1111";
        createMember(email, password);

        //when
        String newEmail = "bb1111@gmail.com";
        ChangeEmailRequest request = new ChangeEmailRequest();
        request.setEmail(newEmail);
        request.setPassword(password);
        memberService.updateEmail(email, request);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(memberService.getMemberByEmail(newEmail).getEmail())
                .isNotNull();
    }

    @Test
    @Transactional
    void updatePasswordTest() {
        //given
        String email = "aa1111@gmail.com";
        String password = "1111";
        createMember(email, password);

        //when
        String newPassword = "1234";
        memberService.updatePassword(newPassword, email);
        em.flush();
        em.clear();

        //then
        boolean notMatchingPassword = memberValidator.isNotMatchingPassword(newPassword, email);
        Assertions
                .assertThat(notMatchingPassword)
                .isFalse();
    }
}