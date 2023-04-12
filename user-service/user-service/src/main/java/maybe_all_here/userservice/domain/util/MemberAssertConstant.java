package maybe_all_here.userservice.domain.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberAssertConstant {

    EMAIL_IS_NULL("이메일이 매핑되지 않았습니다."),
    PASSWORD_IS_NULL("비밀번호가 매핑되지 않았습니다."),
    REAL_NAME_IS_NULL("실명이 매핑되지 않았습니다."),
    AUTH_IS_NULL("권한이 매핑되지 않았습니다.");

    private final String value;
}
