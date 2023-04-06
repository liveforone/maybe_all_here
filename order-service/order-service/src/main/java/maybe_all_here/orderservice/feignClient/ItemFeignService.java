package maybe_all_here.orderservice.feignClient;

import maybe_all_here.orderservice.controller.constant.ParamConstant;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.feignClient.constant.ItemUrl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(ItemUrl.BASE)
public interface ItemFeignService {

    @PostMapping(ItemUrl.ITEM_PROVIDE_DETAIL)
    ItemProvideResponse getItemInfo(@PathVariable(ParamConstant.ITEM_ID) Long itemId);
}
