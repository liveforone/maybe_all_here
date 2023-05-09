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

    public boolean isNotMatchingPassword(String inputPassword, String username) {
        String foundPassword = memberRepository.findPasswordForValidation(username);

        return !passwordEncoder.matches(inputPassword, foundPassword);
    }

    public boolean isDuplicateEmail(String username) {
        Long foundId = memberRepository.findIdForValidation(username);

        return !CommonUtils.isNull(foundId);
    }
}
