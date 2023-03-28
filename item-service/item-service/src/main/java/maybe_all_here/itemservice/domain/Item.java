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
    private Long shopId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private long price;

    private long remaining;

    private long good;
    private long bad;

    @Builder
    public Item(Long id, Long shopId, String title, String content, long price, long remaining, long good, long bad) {
        this.id = id;
        this.shopId = shopId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.remaining = remaining;
        this.good = good;
        this.bad = bad;
    }
}
