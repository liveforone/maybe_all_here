package may_all_here.shopservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    NOT_SELLER("상점 개설은 셀러 회원만 가능합니다.\n일반 회원은 상점 개설이 불가능합니다."),
    DUPLICATE_USER("상점은 한 번만 개설 가능합니다."),
    SHOP_CREATE_SUCCESS("상점 개설에 성공하였습니다.\n축하합니다."),
    SHOP_IS_NULL("상점이 존재하지 않습니다."),
    SHOP_NAME_UPDATE_SUCCESS("상호명 변경에 성공하였습니다."),
    SHOP_ADDRESS_UPDATE_SUCCESS("상점 주소 변경에 성공하였습니다."),
    SHOP_TEL_UPDATE_SUCCESS("상점 전화번호 변경에 성공하였습니다.");

    private final String value;
}
