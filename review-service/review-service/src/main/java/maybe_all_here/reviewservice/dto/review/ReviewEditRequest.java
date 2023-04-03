package maybe_all_here.reviewservice.dto.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewEditRequest {

    @NotBlank(message = "리뷰를 작성하셔야합니다.\n참고로 추천/비추천은 수정이 불가합니다.")
    private String content;
}
