package maybe_all_here.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maybe_all_here.userservice.domain.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {

    private Long id;
    private String email;
    private String realName;
    private long mileagePoint;
    private Role auth;
}
