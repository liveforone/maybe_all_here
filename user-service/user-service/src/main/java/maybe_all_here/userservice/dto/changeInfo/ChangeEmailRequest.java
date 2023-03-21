package maybe_all_here.userservice.dto.changeInfo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeEmailRequest {

    @Email(message = "이메일 형식을 지켜 작성해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 반드시 입력하세요.")
    private String password;
}
