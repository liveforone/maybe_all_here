package maybe_all_here.reviewservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.reviewservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;


}
