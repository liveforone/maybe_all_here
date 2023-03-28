package maybe_all_here.itemservice.dto.uploadFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maybe_all_here.itemservice.domain.Item;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileRequest {

    private Long id;
    private String saveFileName;
    private Item item;
}
