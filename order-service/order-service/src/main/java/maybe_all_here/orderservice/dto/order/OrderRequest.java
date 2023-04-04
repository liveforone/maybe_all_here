package maybe_all_here.orderservice.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import maybe_all_here.orderservice.domain.OrderState;

@Data
@NoArgsConstructor
public class OrderRequest {

    private Long id;
    private String itemTitle;
    private long orderQuantity;
    private long totalPrice;
    private long discountPrice;
    private String email;
    private Long itemId;
    private OrderState orderState;
}
