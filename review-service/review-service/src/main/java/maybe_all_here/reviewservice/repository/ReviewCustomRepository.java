package maybe_all_here.reviewservice.repository;

public interface ReviewCustomRepository {

    void deleteBulkByItemId(Long itemId);
}
