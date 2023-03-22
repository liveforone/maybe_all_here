package may_all_here.shopservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopRequest {

    private Long id;

    @NotBlank(message = "상호명은 반드시 기입해야합니다.")
    private String shopName;

    @NotBlank(message = "주소는 반드시 기입해야합니다.")
    private String address;

    @NotBlank(message = "전화번호는 반드시 기입해야합니다.")
    private String tel;

    @NotBlank(message = "셀러의 이메일은 반드시 기입해야합니다.")
    private String email;
}
