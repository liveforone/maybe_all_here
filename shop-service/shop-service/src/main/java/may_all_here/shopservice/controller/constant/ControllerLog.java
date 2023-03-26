package may_all_here.shopservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    SHOP_CREATE_SUCCESS("상점 개설 성공 || Shop id : "),
    UPDATE_SHOP_NAME_SUCCESS("상호명 변경 성공 || Shop id : ");

    private final String value;
}
