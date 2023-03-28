package maybe_all_here.itemservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRequest {

    private Long id;
    private Long shopId;
    private String title;
    private String content;
    private long price;
    private long remaining;
}
