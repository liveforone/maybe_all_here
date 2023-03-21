package maybe_all_here.userservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.userservice.aop.stopwatch.LogExecutionTime;
import maybe_all_here.userservice.controller.constant.ControllerLog;
import maybe_all_here.userservice.controller.constant.MemberUrl;
import maybe_all_here.userservice.controller.constant.ParamConstant;
import maybe_all_here.userservice.controller.restResponse.RestResponse;
import maybe_all_here.userservice.domain.Role;
import maybe_all_here.userservice.dto.changeInfo.ChangeEmailRequest;
import maybe_all_here.userservice.dto.changeInfo.ChangePasswordRequest;
import maybe_all_here.userservice.dto.response.MemberInfoResponse;
import maybe_all_here.userservice.dto.response.MemberResponse;
import maybe_all_here.userservice.dto.signupAndLogin.MemberLoginRequest;
import maybe_all_here.userservice.dto.signupAndLogin.MemberSignupRequest;
import maybe_all_here.userservice.jwt.TokenInfo;
import maybe_all_here.userservice.jwt.constant.JwtConstant;
import maybe_all_here.userservice.kafka.UserProducer;
import maybe_all_here.userservice.service.MemberService;
import maybe_all_here.userservice.validator.MemberValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final UserProducer userProducer;
    private final MemberValidator memberValidator;

    @GetMapping(MemberUrl.HOME)
    public ResponseEntity<?> home() {
        return RestResponse.home();
    }

    @GetMapping(MemberUrl.SIGNUP)
    public ResponseEntity<?> signupPage() {
        return RestResponse.signupPage();
    }

    @PostMapping(MemberUrl.SIGNUP)
    public ResponseEntity<?> signup(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String email = memberSignupRequest.getEmail();
        if (memberValidator.isDuplicateEmail(email)) {
            return RestResponse.duplicateEmail();
        }

        memberService.signup(memberSignupRequest);
        userProducer.createMileage(email);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @GetMapping(MemberUrl.SELLER_SIGNUP)
    public ResponseEntity<?> signupSellerPage() {
        return RestResponse.signupPage();
    }

    @PostMapping(MemberUrl.SELLER_SIGNUP)
    public ResponseEntity<?> signupSeller(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String email = memberSignupRequest.getEmail();
        if (memberValidator.isDuplicateEmail(email)) {
            return RestResponse.duplicateEmail();
        }

        memberService.signupSeller(memberSignupRequest);
        userProducer.createMileage(email);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @GetMapping(MemberUrl.LOGIN)
    public ResponseEntity<?> loginPage() {
        return RestResponse.loginPage();
    }

    @PostMapping(MemberUrl.LOGIN)
    public ResponseEntity<?> login(
            @RequestBody @Valid MemberLoginRequest memberLoginRequest,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        try {
            TokenInfo tokenInfo = memberService.login(memberLoginRequest);
            log.info(ControllerLog.LOGIN_SUCCESS.getValue());

            response.addHeader(JwtConstant.ACCESS_TOKEN, tokenInfo.getAccessToken());
            response.addHeader(JwtConstant.REFRESH_TOKEN, tokenInfo.getRefreshToken());
            return RestResponse.loginSuccess();
        } catch (BadCredentialsException exception) {
            return RestResponse.loginFail();
        }
    }

    @GetMapping(MemberUrl.MY_PAGE)
    @LogExecutionTime
    public ResponseEntity<MemberInfoResponse> myPage(Principal principal) {
        MemberInfoResponse member = memberService.getMemberInfo(principal.getName());
        return ResponseEntity.ok(member);
    }

    @GetMapping(MemberUrl.USER_INFO)
    @LogExecutionTime
    public ResponseEntity<MemberResponse> userInfo(@PathVariable(ParamConstant.EMAIL) String email) {
        MemberResponse member = memberService.getMemberByEmail(email);

        return ResponseEntity.ok(member);
    }

    @PatchMapping(MemberUrl.CHANGE_EMAIL)
    public ResponseEntity<?> changeEmail(
            @RequestBody @Valid ChangeEmailRequest changeEmailRequest,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        if (memberValidator.isDuplicateEmail(changeEmailRequest.getEmail())) {
            return RestResponse.duplicateEmail();
        }

        String email = principal.getName();
        memberService.updateEmail(email, changeEmailRequest);
        log.info(ControllerLog.CHANGE_EMAIL_SUCCESS.getValue());

        return RestResponse.changeEmailSuccess();
    }

    @PatchMapping(MemberUrl.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String inputPw = changePasswordRequest.getOldPassword();
        String email = principal.getName();
        if (memberValidator.isNotMatchingPassword(inputPw, email)) {
            return RestResponse.notMatchPassword();
        }

        String requestPw = changePasswordRequest.getNewPassword();
        memberService.updatePassword(requestPw, email);
        log.info(ControllerLog.CHANGE_PASSWORD_SUCCESS.getValue());

        return RestResponse.changePasswordSuccess();
    }
    @DeleteMapping(MemberUrl.WITHDRAW)
    public ResponseEntity<?> withdraw(
            @RequestBody String password,
            Principal principal
    ) {
        String email = principal.getName();
        if (memberValidator.isNotMatchingPassword(password, email)) {
            return RestResponse.notMatchPassword();
        }

        memberService.deleteUser(email);
        log.info(ControllerLog.WITHDRAW_SUCCESS.getValue() + email);

        return RestResponse.withdrawSuccess();
    }

    @GetMapping(MemberUrl.ADMIN)
    public ResponseEntity<?> adminPage(Principal principal) {
        MemberResponse foundMember = memberService.getMemberByEmail(principal.getName());

        if (!foundMember.getAuth().equals(Role.ADMIN)) {
            log.error(ControllerLog.ADMIN_FAIL.getValue());
            return RestResponse.prohibition();
        }

        List<MemberResponse> allMembers = memberService.getAllMemberForAdmin();
        log.info(ControllerLog.ADMIN_SUCCESS.getValue());

        return ResponseEntity.ok(allMembers);
    }

    @GetMapping(MemberUrl.PROHIBITION)
    public ResponseEntity<?> prohibition() {
        return RestResponse.prohibition();
    }
}
