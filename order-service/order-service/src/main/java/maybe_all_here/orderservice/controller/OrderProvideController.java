package maybe_all_here.orderservice.controller;

import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.controller.constant.OrderProvideUrl;
import maybe_all_here.orderservice.controller.constant.ProvideParamConstant;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderProvideController {

    private final OrderService orderService;

    @GetMapping(OrderProvideUrl.ORDER_INFO)
    public ResponseEntity<?> getOrderInfo(
            @PathVariable(ProvideParamConstant.EMAIL) String email,
            @PathVariable(ProvideParamConstant.ITEM_ID) Long itemId
    ) {
        OrderProvideResponse order = orderService.getOrderByEmailAndItemId(email, itemId);
        return ResponseEntity.ok(order);
    }
}
