package maybe_all_here.itemservice.repository.uploadFile;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UploadFileRepositoryImpl {

    private final JPAQueryFactory queryFactory;
}
