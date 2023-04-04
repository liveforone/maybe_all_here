package maybe_all_here.orderservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.orderservice.authentication.AuthenticationInfo;
import maybe_all_here.orderservice.controller.constant.OrderUrl;
import maybe_all_here.orderservice.controller.constant.ParamConstant;
import maybe_all_here.orderservice.dto.mileage.MileageResponse;
import maybe_all_here.orderservice.feignClient.MileageFeignService;
import maybe_all_here.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    public final MileageFeignService mileageFeignService;
    private final AuthenticationInfo authenticationInfo;

    @GetMapping(OrderUrl.ORDER)
    public ResponseEntity<?> orderPage(HttpServletRequest request) {
        String email = authenticationInfo.getEmail(request);
        MileageResponse mileage = mileageFeignService.getMileageInfoGet(email);

        return ResponseEntity.ok(mileage);
    }
    //post도 url 같음.
}
