package maybe_all_here.reviewservice.dto.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequest {

    private Long id;
    private Long itemId;
    private String email;
    private Long orderId;

    @NotBlank(message = "리뷰는 공백으로 제출할 수 없습니다.\n회원님의 생각을 잘 담아주세요.")
    private String content;

    @NotBlank(message = "추천/비추천은 반드시 표시해야합니다.")
    private String recommend;
}
