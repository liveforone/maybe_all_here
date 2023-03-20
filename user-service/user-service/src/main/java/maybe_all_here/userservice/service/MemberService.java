package maybe_all_here.userservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.userservice.domain.Role;
import maybe_all_here.userservice.dto.ChangeEmailRequest;
import maybe_all_here.userservice.dto.MemberLoginRequest;
import maybe_all_here.userservice.dto.MemberResponse;
import maybe_all_here.userservice.dto.MemberSignupRequest;
import maybe_all_here.userservice.jwt.JwtTokenProvider;
import maybe_all_here.userservice.jwt.TokenInfo;
import maybe_all_here.userservice.repository.MemberRepository;
import maybe_all_here.userservice.service.util.MemberMapper;
import maybe_all_here.userservice.service.util.PasswordUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN = "admin@intelligentBank.com";

    public MemberResponse getMemberByEmail(String email) {
        return MemberMapper.entityToDto(memberRepository.findByEmail(email));
    }

    /*
     * 모든 유저 반환
     * when : 권한이 어드민인 유저가 호출할때
     */
    public List<MemberResponse> getAllMemberForAdmin() {
        return MemberMapper.entityToDtoList(memberRepository.findAll());
    }

    @Transactional
    public void signup(MemberSignupRequest memberSignupRequest) {
        memberSignupRequest.setPassword(
                PasswordUtils.encodePassword(memberSignupRequest.getPassword())
        );

        if (Objects.equals(memberSignupRequest.getEmail(), ADMIN)) {
            memberSignupRequest.setAuth(Role.ADMIN);
        } else {
            memberSignupRequest.setAuth(Role.MEMBER);
        }

        memberRepository.save(MemberMapper.dtoToEntity(memberSignupRequest));
    }

    @Transactional
    public void signupSeller(MemberSignupRequest memberSignupRequest) {
        memberSignupRequest.setPassword(
                PasswordUtils.encodePassword(memberSignupRequest.getPassword())
        );

        memberSignupRequest.setAuth(Role.SELLER);

        memberRepository.save(MemberMapper.dtoToEntity(memberSignupRequest));
    }

    @Transactional
    public TokenInfo login(MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String password = memberLoginRequest.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        return jwtTokenProvider
                .generateToken(authentication);
    }

    @Transactional
    public void updateEmail(String email, ChangeEmailRequest changeEmailRequest) {
        String newEmail = changeEmailRequest.getEmail();
        memberRepository.updateEmail(email, newEmail);
    }

    @Transactional
    public void updatePassword(String inputPassword, String email) {
        String newPassword = PasswordUtils.encodePassword(inputPassword);
        memberRepository.updatePassword(newPassword, email);
    }

    @Transactional
    public void deleteUser(String email) {
        memberRepository.deleteByEmail(email);
    }
}
