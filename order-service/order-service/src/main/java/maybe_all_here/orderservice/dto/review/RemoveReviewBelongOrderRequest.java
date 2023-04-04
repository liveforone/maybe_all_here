package maybe_all_here.orderservice.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoveReviewBelongOrderRequest {

    String email;
    Long itemId;
}
