package maybe_all_here.userservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.userservice.async.AsyncConstant;
import maybe_all_here.userservice.domain.Member;
import maybe_all_here.userservice.dto.changeInfo.ChangeEmailRequest;
import maybe_all_here.userservice.dto.mileage.MileageResponse;
import maybe_all_here.userservice.dto.response.MemberInfoResponse;
import maybe_all_here.userservice.dto.signupAndLogin.MemberLoginRequest;
import maybe_all_here.userservice.dto.response.MemberResponse;
import maybe_all_here.userservice.dto.signupAndLogin.MemberSignupRequest;
import maybe_all_here.userservice.feignClient.MileageFeignService;
import maybe_all_here.userservice.jwt.JwtTokenProvider;
import maybe_all_here.userservice.jwt.TokenInfo;
import maybe_all_here.userservice.repository.MemberRepository;
import maybe_all_here.userservice.service.constant.CircuitLog;
import maybe_all_here.userservice.service.util.MemberMapper;
import maybe_all_here.userservice.service.util.PasswordUtils;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MileageFeignService mileageFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public MemberResponse getMemberByEmail(String email) {
        return MemberMapper.entityToDto(memberRepository.findByEmail(email));
    }

    public MemberInfoResponse getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email);
        MileageResponse mileage = getMyMileage(email);

        return MemberMapper.createMemberInfo(member, mileage);
    }

    private MileageResponse getMyMileage(String email) {
        return circuitBreakerFactory
                .create(CircuitLog.MEMBER_CIRCUIT_LOG.getValue())
                .run(() -> mileageFeignService.getMyMileage(email),
                        throwable -> new MileageResponse()
                );
    }

    public List<MemberResponse> getAllMemberForAdmin() {
        return MemberMapper.entityToDtoList(memberRepository.findAll());
    }


    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void signup(MemberSignupRequest memberSignupRequest) {
        Member member = Member.builder().build();
        member.signup(memberSignupRequest);

        memberRepository.save(member);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void signupSeller(MemberSignupRequest memberSignupRequest) {
        Member member = Member.builder().build();
        member.signupSeller(memberSignupRequest);

        memberRepository.save(member);
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
    @Async(AsyncConstant.commandAsync)
    public void updateEmail(String email, ChangeEmailRequest changeEmailRequest) {
        String newEmail = changeEmailRequest.getEmail();
        memberRepository.updateEmail(email, newEmail);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updatePassword(String inputPassword, String email) {
        String newPassword = PasswordUtils.encodePassword(inputPassword);
        memberRepository.updatePassword(newPassword, email);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void deleteUser(String email) {
        memberRepository.deleteByEmail(email);
    }
}
