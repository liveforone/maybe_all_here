package maybe_all_here.itemservice.repository.uploadFile;

import maybe_all_here.itemservice.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long>, UploadFileCustomRepository {
}
