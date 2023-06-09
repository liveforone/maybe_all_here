package maybe_all_here.orderservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    ITEM_IS_NULL("존재하지 않는 상품입니다."),
    ITEM_IS_SOLD_OUT("상품의 재고가 모두 소진되었습니다."),
    OVER_REMAINING("주문 수량이 재고를 초과합니다."),
    OVER_MILEAGE("요청하신 할인금액이 보유하신 마일리지를 초과합니다."),
    ORDER_SUCCESS("주문 성공하였습니다."),
    ORDER_IS_OVER_DATE("7일이 지나 주문 취소가 불가능합니다."),
    NOT_OWNER_OF_ORDER("주문자가 아닙니다."),
    ORDER_CANCEL_SUCCESS("주문을 성공적으로 취소하였습니다.");

    private final String value;
}
