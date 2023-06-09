package maybe_all_here.reviewservice.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private String email;
    private Long orderId;
    private String content;
    private String recommend;
    private LocalDate createdDate;
}
