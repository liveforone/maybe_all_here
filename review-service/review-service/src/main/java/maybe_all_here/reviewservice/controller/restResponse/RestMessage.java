package maybe_all_here.reviewservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    NULL_ORDER("주문이 존재하지 않습니다."),
    CANCEL_ORDER("주문이 취소되어 리뷰작성이 불가능합니다."),
    CREATE_REVIEW_SUCCESS("리뷰가 성공적으로 등록되었습니다."),
    REVIEW_IS_NULL("리뷰가 존재하지 않습니다."),
    NOT_OWNER("수정은 작성자만 가능합니다.");

    private final String value;
}
