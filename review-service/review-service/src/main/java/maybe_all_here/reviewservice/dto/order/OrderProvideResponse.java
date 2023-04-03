package maybe_all_here.reviewservice.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProvideResponse {

    private Long id;
    private OrderState orderState;
    private Long itemId;
}
