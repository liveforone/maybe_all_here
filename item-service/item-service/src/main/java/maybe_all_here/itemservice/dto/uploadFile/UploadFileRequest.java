package maybe_all_here.itemservice.dto.uploadFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import maybe_all_here.itemservice.domain.Item;

@Data
@NoArgsConstructor
public class UploadFileRequest {

    private Long id;
    private String saveFileName;
    private Item item;
}
