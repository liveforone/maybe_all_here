package maybe_all_here.orderservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    ITEM_IS_SOLD_OUT("상품의 재고가 모두 소진되었습니다.");

    private final String value;
}
