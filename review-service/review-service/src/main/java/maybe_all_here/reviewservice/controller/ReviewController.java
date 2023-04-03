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
import maybe_all_here.reviewservice.dto.review.ReviewResponse;
import maybe_all_here.reviewservice.feignClient.OrderFeignService;
import maybe_all_here.reviewservice.service.ReviewService;
import maybe_all_here.reviewservice.utility.CommonUtils;
import maybe_all_here.reviewservice.validator.ReviewValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthenticationInfo authenticationInfo;
    private final OrderFeignService orderFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;
    private final ReviewValidator reviewValidator;

    @GetMapping(ReviewUrl.REVIEWS_BELONG_TO_ITEM)
    public ResponseEntity<?> getReviewsByItem(
            @PathVariable(ParamConstant.ITEM_ID) Long itemId,
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ReviewResponse> reviews = reviewService.getReviewsByItemId(itemId, lastId, pageSize);

        return ResponseEntity.ok(reviews);
    }

    @GetMapping(ReviewUrl.REVIEW_DETAIL)
    public ResponseEntity<?> reviewDetail(
            @PathVariable(ParamConstant.REVIEW_ID) Long reviewId
    ) {
        if (reviewValidator.isNullReview(reviewId)) {
            return RestResponse.reviewIsNull();
        }

        ReviewResponse review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }
    //리뷰 수정
    //리뷰 삭제

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
