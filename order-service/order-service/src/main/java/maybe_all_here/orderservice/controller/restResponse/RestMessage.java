package maybe_all_here.orderservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    ITEM_IS_NULL("존재하지 않는 상품입니다."),
    ITEM_IS_SOLD_OUT("상품의 재고가 모두 소진되었습니다."),
    OVER_REMAINING("주문 수량이 재고를 초과합니다.");

    private final String value;
}
