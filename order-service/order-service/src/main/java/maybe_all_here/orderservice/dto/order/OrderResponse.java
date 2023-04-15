package maybe_all_here.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maybe_all_here.orderservice.domain.OrderState;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String itemTitle;
    private long orderQuantity;
    private long totalPrice;
    private long discountedPrice;
    private OrderState orderState;
    private LocalDate createdDate;
}
