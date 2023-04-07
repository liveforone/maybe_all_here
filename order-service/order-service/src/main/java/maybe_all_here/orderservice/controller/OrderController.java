package maybe_all_here.orderservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.orderservice.authentication.AuthenticationInfo;
import maybe_all_here.orderservice.controller.constant.CircuitLog;
import maybe_all_here.orderservice.controller.constant.ControllerLog;
import maybe_all_here.orderservice.controller.constant.OrderUrl;
import maybe_all_here.orderservice.controller.constant.ParamConstant;
import maybe_all_here.orderservice.controller.restResponse.RestResponse;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.mileage.MileageResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.dto.order.OrderResponse;
import maybe_all_here.orderservice.feignClient.ItemFeignService;
import maybe_all_here.orderservice.feignClient.MileageFeignService;
import maybe_all_here.orderservice.service.OrderService;
import maybe_all_here.orderservice.utility.CommonUtils;
import maybe_all_here.orderservice.validator.OrderValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    public final MileageFeignService mileageFeignService;
    private final ItemFeignService itemFeignService;
    private final AuthenticationInfo authenticationInfo;
    private final OrderValidator orderValidator;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @GetMapping(OrderUrl.ORDER_LIST)
    public ResponseEntity<?> orders(
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize,
            HttpServletRequest request
    ) {
        String email = authenticationInfo.getEmail(request);
        List<OrderResponse> orders = orderService.getOrdersByEmail(email, lastId, pageSize);

        return ResponseEntity.ok(orders);
    }

    @GetMapping(OrderUrl.ORDER_DETAIL)
    public ResponseEntity<?> orderDetail(
            @PathVariable(ParamConstant.ORDER_ID) Long orderId
    ) {
        OrderResponse order = orderService.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    @GetMapping(OrderUrl.ORDER)
    public ResponseEntity<?> orderPage(HttpServletRequest request) {
        String email = authenticationInfo.getEmail(request);
        MileageResponse mileage = getMileageGetMethod(email);

        return ResponseEntity.ok(mileage);
    }

    private MileageResponse getMileageGetMethod(String email) {
        return circuitBreakerFactory
                .create(CircuitLog.ORDER_CIRCUIT_LOG.getValue())
                .run(() -> mileageFeignService.getMileageInfoGet(email),
                        throwable -> new MileageResponse()
                );
    }

    @PostMapping(OrderUrl.ORDER)
    public ResponseEntity<?> order(
            @RequestBody OrderRequest orderRequest,
            BindingResult bindingResult,
            @PathVariable(ParamConstant.ITEM_ID) Long itemId,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        ItemProvideResponse item = getItemInfo(itemId);

        if (CommonUtils.isNull(item)) {
            return RestResponse.itemIsNull();
        }

        if (orderValidator.isSoldOut(item)) {
            return RestResponse.itemIsSoldOut();
        }

        if (orderValidator.isOverRemaining(orderRequest, item)) {
            return RestResponse.overRemaining();
        }

        String email = authenticationInfo.getEmail(request);
        MileageResponse mileage = getMileagePostMethod(email);

        if (orderValidator.isOverMileage(orderRequest, mileage)) {
            return RestResponse.overMileage();
        }

        Long orderId = orderService.order(orderRequest, item, email);
        log.info(ControllerLog.ORDER_SUCCESS.getValue() + orderId);

        return RestResponse.orderSuccess();
    }

    private ItemProvideResponse getItemInfo(Long itemId) {
        return circuitBreakerFactory
                .create(CircuitLog.ORDER_CIRCUIT_LOG.getValue())
                .run(() -> itemFeignService.getItemInfo(itemId),
                        throwable -> new ItemProvideResponse()
                );
    }

    private MileageResponse getMileagePostMethod(String email) {
        return circuitBreakerFactory
                .create(CircuitLog.ORDER_CIRCUIT_LOG.getValue())
                .run(() -> mileageFeignService.getMileageInfoPost(email),
                        throwable -> new MileageResponse()
                );
    }

    @PatchMapping(OrderUrl.ORDER_CANCEL)
    public ResponseEntity<?> orderCancel(
            @PathVariable(ParamConstant.ORDER_ID) Long orderId,
            HttpServletRequest request
    ) {
        String email = authenticationInfo.getEmail(request);
        if (orderValidator.isNotOwner(email, orderId)) {
            return RestResponse.isNotOwner();
        }

        if (orderValidator.isOverCancelLimitDate(orderId)) {
            return RestResponse.overCancelDate();
        }

        orderService.cancelOrder(orderId);
        log.info(ControllerLog.ORDER_CANCEL_SUCCESS.getValue() + orderId);

        return RestResponse.orderCancelSuccess();
    }
}
