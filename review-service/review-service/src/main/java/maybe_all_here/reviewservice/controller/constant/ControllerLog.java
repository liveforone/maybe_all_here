package maybe_all_here.reviewservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_REVIEW_SUCCESS("리뷰 작성에 성공했습니다."),
    EDIT_REVIEW_SUCCESS("Review Edit Success | id : ");

    private final String value;
}
