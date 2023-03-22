package may_all_here.shopservice.feignClient;

import may_all_here.shopservice.dto.member.MemberResponse;
import may_all_here.shopservice.feignClient.constant.MemberParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = MemberUrl.BASE)
public interface MemberFeignService {

    @GetMapping(MemberUrl.USER_INFO)
    MemberResponse getMemberInfo(@PathVariable(MemberParam.EMAIL) String email);
}
