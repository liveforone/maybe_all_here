package maybe_all_here.orderservice.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @Positive(message = "주문 수량은 반드시 0 이상이여야 합니다.")
    @NotNull(message = "주문하려는 수량을 반드시 기재해주세요.")
    private long orderQuantity;

    @PositiveOrZero(message = "사용하려는 마일리지는 양수로 작성해주세요.")
    @NotNull(message = "사용하려는 마일리지는 반드시 기재해주세요.")
    private long spentMileage;
}
