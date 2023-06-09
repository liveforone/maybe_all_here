package maybe_all_here.itemservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    NULL_ITEM("존재하지 않는 상품입니다."),
    CREATE_ITEM_SUCCESS("상품 등록에 성공하셨습니다."),
    EDIT_TITLE_SUCCESS("수정한 제목이 정상적으로 반영되었습니다."),
    EDIT_FILE_SUCCESS("수정한 파일이 정상적으로 반영되었습니다."),
    EDIT_CONTENT_SUCCESS("수정한 내용이 정상적으로 반영되었습니다."),
    EDIT_PRICE_SUCCESS("수정한 가격이 정상적으로 반영되었습니다."),
    EDIT_REMAINING_SUCCESS("수정한 수량이 정상적으로 반영되었습니다."),
    DELETE_ITEM_SUCCESS("상품이 정상적으로 삭제되었습니다."),
    NOT_SELLER("셀러 회원이 아닙니다.");

    private final String value;
}
