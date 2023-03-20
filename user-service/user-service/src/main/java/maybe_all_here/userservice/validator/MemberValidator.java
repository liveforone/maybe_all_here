package maybe_all_here.userservice.validator;

import lombok.RequiredArgsConstructor;
import maybe_all_here.userservice.domain.Member;
import maybe_all_here.userservice.repository.MemberRepository;
import maybe_all_here.userservice.utility.CommonUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean isNotMatchingPassword(String inputPassword, String email) {
        Member foundMember = memberRepository.findByEmail(email);
        String originalPassword = foundMember.getPassword();

        return !passwordEncoder.matches(inputPassword, originalPassword);
    }

    public boolean isDuplicateEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        return !CommonUtils.isNull(member);
    }
}
