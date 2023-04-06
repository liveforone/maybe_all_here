package maybe_all_here.orderservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.orderservice.authentication.AuthenticationInfo;
import maybe_all_here.orderservice.controller.constant.CircuitLog;
import maybe_all_here.orderservice.controller.constant.OrderUrl;
import maybe_all_here.orderservice.controller.constant.ParamConstant;
import maybe_all_here.orderservice.controller.restResponse.RestResponse;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.mileage.MileageResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.feignClient.ItemFeignService;
import maybe_all_here.orderservice.feignClient.MileageFeignService;
import maybe_all_here.orderservice.service.OrderService;
import maybe_all_here.orderservice.utility.CommonUtils;
import maybe_all_here.orderservice.validator.OrderValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @PathVariable(ParamConstant.ITEM_ID) Long itemId,
            HttpServletRequest request
    ) {
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
    //post도 url 같음. 총 주문 가격 계산해야함(클래스 만들어서) | 총가격 = (수량 * 가격)
    //할인 가격 : 사용한 마일리지
    //order cancel, hypermart에서 order clock, cancel 등의 클래스 참고해서 제약걸리
}
