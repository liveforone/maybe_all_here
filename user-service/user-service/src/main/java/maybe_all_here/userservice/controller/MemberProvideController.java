package maybe_all_here.userservice.controller;

import lombok.RequiredArgsConstructor;
import maybe_all_here.userservice.aop.stopwatch.LogExecutionTime;
import maybe_all_here.userservice.controller.constant.MemberUrl;
import maybe_all_here.userservice.controller.constant.ParamConstant;
import maybe_all_here.userservice.dto.response.MemberResponse;
import maybe_all_here.userservice.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberProvideController {

    private final MemberService memberService;

    @PostMapping(MemberUrl.USER_INFO)
    @LogExecutionTime
    public ResponseEntity<MemberResponse> userInfo(@PathVariable(ParamConstant.EMAIL) String email) {
        MemberResponse member = memberService.getMemberByEmail(email);

        return ResponseEntity.ok(member);
    }
}
