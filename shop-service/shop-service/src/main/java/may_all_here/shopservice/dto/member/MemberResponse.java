package may_all_here.shopservice.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponse {

    private Long id;
    private String email;
    private String realName;
    private Role auth;
}
