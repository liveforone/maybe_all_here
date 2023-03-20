package maybe_all_here.userservice.service;

import jakarta.persistence.EntityManager;
import maybe_all_here.userservice.domain.Role;
import maybe_all_here.userservice.dto.MemberSignupRequest;
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
    void updateEmailTest() {
    }

    @Test
    void updatePassword() {
    }
}