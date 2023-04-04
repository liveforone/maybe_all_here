package maybe_all_here.orderservice.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import maybe_all_here.orderservice.domain.OrderState;

@Data
@NoArgsConstructor
public class OrderProvideResponse {

    private Long id;
    private OrderState orderState;
    private Long itemId;
}
