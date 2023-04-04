package maybe_all_here.reviewservice.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoveReviewBelongOrderRequest {

    String email;
    Long itemId;
}
