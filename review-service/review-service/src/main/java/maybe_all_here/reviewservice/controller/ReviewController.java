package maybe_all_here.reviewservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.reviewservice.authentication.AuthenticationInfo;
import maybe_all_here.reviewservice.controller.constant.CircuitLog;
import maybe_all_here.reviewservice.controller.constant.ControllerLog;
import maybe_all_here.reviewservice.controller.constant.ParamConstant;
import maybe_all_here.reviewservice.controller.constant.ReviewUrl;
import maybe_all_here.reviewservice.controller.restResponse.RestResponse;
import maybe_all_here.reviewservice.dto.order.OrderProvideResponse;
import maybe_all_here.reviewservice.dto.review.ReviewRequest;
import maybe_all_here.reviewservice.feignClient.OrderFeignService;
import maybe_all_here.reviewservice.service.ReviewService;
import maybe_all_here.reviewservice.utility.CommonUtils;
import maybe_all_here.reviewservice.validator.ReviewValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthenticationInfo authenticationInfo;
    private final OrderFeignService orderFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;
    private final ReviewValidator reviewValidator;

    @PostMapping(ReviewUrl.CREATE_REVIEW)
    public ResponseEntity<?> createReview(
            @PathVariable(ParamConstant.ITEM_ID) Long itemId,
            @RequestBody ReviewRequest reviewRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String email = authenticationInfo.getEmail(request);
        OrderProvideResponse order = getOrder(email, itemId);

        if (CommonUtils.isNull(order)) {
            return RestResponse.orderIsNull();
        }

        if (reviewValidator.isCancelOrder(order)) {
            return RestResponse.orderCanceled();
        }

        reviewService.createReview(reviewRequest, email, itemId);
        log.info(ControllerLog.CREATE_REVIEW_SUCCESS.getValue());

        return RestResponse.createReviewSuccess();
    }

    private OrderProvideResponse getOrder(String email, Long itemId) {
        return circuitBreakerFactory
                .create(CircuitLog.REVIEW_CIRCUIT_LOG.getValue())
                .run(() -> orderFeignService.getOrderInfo(email, itemId),
                        throwable -> new OrderProvideResponse()
                );
    }
}
