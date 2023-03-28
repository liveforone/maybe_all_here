package maybe_all_here.itemservice.dto.file;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class FileSaveRequest {

    private List<MultipartFile> files;
    private Long itemId;
}
