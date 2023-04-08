package maybe_all_here.orderservice.controller;

import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.controller.constant.OrderProvideUrl;
import maybe_all_here.orderservice.controller.constant.ParamConstant;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderProvideController {

    private final OrderService orderService;

    @PostMapping(OrderProvideUrl.ORDER_INFO)
    public ResponseEntity<?> getOrderInfo(@PathVariable(ParamConstant.ORDER_ID) Long orderId) {
        OrderProvideResponse order = orderService.getProvideOrderById(orderId);
        return ResponseEntity.ok(order);
    }
}
