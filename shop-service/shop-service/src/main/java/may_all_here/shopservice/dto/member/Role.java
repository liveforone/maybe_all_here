package may_all_here.shopservice.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),
    SELLER("ROLE_SELLER");

    private String value;
}
