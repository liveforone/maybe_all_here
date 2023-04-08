package maybe_all_here.reviewservice.feignClient;

import maybe_all_here.reviewservice.dto.order.OrderProvideResponse;
import maybe_all_here.reviewservice.feignClient.constant.OrderUrl;
import maybe_all_here.reviewservice.feignClient.constant.ProvideParamConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = OrderUrl.BASE)
public interface OrderFeignService {

    @PostMapping(OrderUrl.ORDER_INFO)
    OrderProvideResponse getOrderInfo(
            @PathVariable(ProvideParamConstant.ORDER_ID) Long orderId
    );
}
