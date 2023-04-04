package maybe_all_here.orderservice.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveReviewBelongOrderRequest {

    String email;
    Long itemId;
}
