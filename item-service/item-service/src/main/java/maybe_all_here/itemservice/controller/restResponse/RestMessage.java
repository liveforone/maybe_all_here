package maybe_all_here.itemservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    NULL_ITEM("존재하지 않는 상품입니다."),
    CREATE_ITEM_SUCCESS("상품 등록에 성공하셨습니다.");

    private final String value;
}
