package maybe_all_here.reviewservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maybe_all_here.reviewservice.dto.review.ReviewRequest;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, updatable = false)
    private String recommend;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    @Builder
    public Review(Long id, Long itemId, String email, Long orderId, String content, String recommend) {
        this.id = id;
        this.itemId = itemId;
        this.email = email;
        this.orderId = orderId;
        this.content = content;
        this.recommend = recommend;
    }

    public void create(ReviewRequest reviewRequest, String email, Long itemId) {
        this.itemId = itemId;
        this.email = email;
        this.orderId = reviewRequest.getOrderId();
        this.content = reviewRequest.getContent();
        this.recommend = reviewRequest.getRecommend();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
