package maybe_all_here.itemservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private Long shopId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private long price;

    private long remaining;

    @Builder
    public Item(Long id, String email, Long shopId, String title, String content, long price, long remaining) {
        this.id = id;
        this.email = email;
        this.shopId = shopId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.remaining = remaining;
    }
}
