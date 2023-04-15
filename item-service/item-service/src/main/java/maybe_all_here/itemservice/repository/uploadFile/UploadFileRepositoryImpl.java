package maybe_all_here.itemservice.repository.uploadFile;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.domain.Item;
import maybe_all_here.itemservice.domain.QItem;
import maybe_all_here.itemservice.domain.QUploadFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UploadFileRepositoryImpl implements UploadFileCustomRepository {

    private final JPAQueryFactory queryFactory;
    QUploadFile uploadFile = QUploadFile.uploadFile;
    QItem qItem = QItem.item;

    public List<String> findFilesByItem(Item item) {
        return queryFactory
                .select(Projections.constructor(
                        String.class,
                        uploadFile.saveFileName)
                )
                .from(uploadFile)
                .join(uploadFile.item, qItem)
                .where(qItem.eq(item))
                .orderBy(uploadFile.id.asc())
                .fetch();
    }

    public List<String> findFilesByItemId(Long itemId) {
        return queryFactory
                .select(Projections.constructor(
                        String.class,
                        uploadFile.saveFileName)
                )
                .from(uploadFile)
                .join(uploadFile.item, qItem)
                .where(uploadFile.item.id.eq(itemId))
                .orderBy(uploadFile.id.asc())
                .fetch();
    }

    public void deleteBulkFileByItem(Item item) {
        queryFactory.delete(uploadFile)
                .where(uploadFile.item.eq(item))
                .execute();
    }

    public void deleteBulkFileByItemId(Long itemId) {
        queryFactory.delete(uploadFile)
                .where(uploadFile.item.id.eq(itemId))
                .execute();
    }
}
