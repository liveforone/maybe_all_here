package maybe_all_here.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maybe_all_here.orderservice.domain.OrderState;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProvideResponse {

    private Long id;
    private OrderState orderState;
    private Long itemId;
}
