package maybe_all_here.itemservice.dto.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRemainingRequest {

    private Long itemId;
    private long orderQuantity;
}
