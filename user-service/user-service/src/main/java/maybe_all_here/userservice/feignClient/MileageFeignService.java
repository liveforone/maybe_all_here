package maybe_all_here.userservice.feignClient;

import maybe_all_here.userservice.dto.mileage.MileageResponse;
import maybe_all_here.userservice.feignClient.constant.ParamConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = MileageUrl.BASE)
public interface MileageFeignService {

    @GetMapping(MileageUrl.MY_MILEAGE)
    MileageResponse getMyMileage(@PathVariable(ParamConstant.EMAIL) String email);
}