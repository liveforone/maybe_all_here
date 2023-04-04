package maybe_all_here.orderservice.feignClient;

import maybe_all_here.orderservice.dto.mileage.MileageResponse;
import maybe_all_here.orderservice.feignClient.constant.MileageParamConstant;
import maybe_all_here.orderservice.feignClient.constant.MileageUrl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = MileageUrl.BASE)
public interface MileageFeignService {

    @GetMapping(MileageUrl.MILEAGE_INFO)
    MileageResponse getMileageInfoGet(@PathVariable(MileageParamConstant.EMAIL) String email);

    @PostMapping(MileageUrl.MILEAGE_INFO)
    MileageResponse getMileageInfoPost(@PathVariable(MileageParamConstant.EMAIL) String email);
}
