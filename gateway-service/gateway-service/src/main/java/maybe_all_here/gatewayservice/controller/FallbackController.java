package maybe_all_here.gatewayservice.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(FallbackUrl.BASE)
public class FallbackController {

    @GetMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserGet() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @PostMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserPost() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @PatchMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserPatch() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @DeleteMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserDelete() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @GetMapping(FallbackUrl.MILEAGE)
    public Mono<String> fallbackMileageGet() {
        return Mono.just(FallbackMessage.MILEAGE_LOG);
    }

    @GetMapping(FallbackUrl.SHOP)
    public Mono<String> fallbackShopGet() {
        return Mono.just(FallbackMessage.SHOP_LOG);
    }

    @PostMapping(FallbackUrl.SHOP)
    public Mono<String> fallbackShopPost() {
        return Mono.just(FallbackMessage.SHOP_LOG);
    }

    @PatchMapping(FallbackUrl.SHOP)
    public Mono<String> fallbackShopPatch() {
        return Mono.just(FallbackMessage.SHOP_LOG);
    }

    @GetMapping(FallbackUrl.ITEM)
    public Mono<String> fallbackItemGet() {
        return Mono.just(FallbackMessage.ITEM_LOG);
    }

    @PostMapping(FallbackUrl.ITEM)
    public Mono<String> fallbackItemPost() {
        return Mono.just(FallbackMessage.ITEM_LOG);
    }

    @PatchMapping(FallbackUrl.ITEM)
    public Mono<String> fallbackItemPatch() {
        return Mono.just(FallbackMessage.ITEM_LOG);
    }

    @DeleteMapping(FallbackUrl.ITEM)
    public Mono<String> fallbackItemDelete() {
        return Mono.just(FallbackMessage.ITEM_LOG);
    }

    @GetMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewGet() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }

    @PostMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewPost() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }

    @PatchMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewPatch() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }

    @DeleteMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewDelete() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }
}
