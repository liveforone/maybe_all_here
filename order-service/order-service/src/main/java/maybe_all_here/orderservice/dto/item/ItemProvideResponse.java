package maybe_all_here.orderservice.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemProvideResponse {

    private Long id;
    private String title;
    private long itemPrice;
    private long remaining;
}
