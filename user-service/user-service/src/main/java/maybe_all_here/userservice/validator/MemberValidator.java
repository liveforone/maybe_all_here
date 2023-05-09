package maybe_all_here.userservice.validator;

import lombok.RequiredArgsConstructor;
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
        String foundPassword = memberRepository.findPasswordForValidation(email);

        return !passwordEncoder.matches(inputPassword, foundPassword);
    }

    public boolean isDuplicateEmail(String email) {
        Long foundId = memberRepository.findIdForValidation(email);

        return !CommonUtils.isNull(foundId);
    }
}
