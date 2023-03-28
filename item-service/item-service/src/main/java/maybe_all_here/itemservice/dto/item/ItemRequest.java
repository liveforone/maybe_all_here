package maybe_all_here.itemservice.dto.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRequest {

    private Long id;
    private Long shopId;

    @NotBlank(message = "상품 이름은 반드시 작성해주세요.")
    private String title;

    @NotBlank(message = "상품 상세 내용은 반드시 작성해주세요.")
    private String content;

    @NotBlank(message = "상품 가격은 반드시 입력해주세요.")
    private long price;

    @NotBlank(message = "상품 재고 수량은 반드시 입력해주세요.")
    private long remaining;
}
