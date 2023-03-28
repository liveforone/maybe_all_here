package maybe_all_here.itemservice.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResponse {

    private Long id;
    private Long shopId;
    private String title;
    private String content;
    private long price;
    private long remaining;
    private long good;
    private long bad;
    private List<String> saveFileName;
}
