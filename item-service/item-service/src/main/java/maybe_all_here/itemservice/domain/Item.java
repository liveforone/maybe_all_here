package maybe_all_here.itemservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maybe_all_here.itemservice.dto.item.ItemRemainingRequest;
import maybe_all_here.itemservice.dto.item.ItemRequest;

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

    public void create(ItemRequest itemRequest, Long shopId) {
        itemRequest.setShopId(shopId);

        this.id = itemRequest.getId();
        this.shopId = itemRequest.getShopId();
        this.title = itemRequest.getTitle();
        this.content = itemRequest.getContent();
        this.price = itemRequest.getPrice();
        this.remaining = itemRequest.getRemaining();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePrice(long price) {
        this.price = price;
    }

    public void increaseRemaining(long remaining) {
        this.remaining += remaining;
    }

    public void decreaseRemaining(ItemRemainingRequest itemRemainingRequest) {
        this.remaining -= itemRemainingRequest.getOrderQuantity();
    }

    public void increaseGood() {
        this.good += ItemConstant.RECOMMEND;
    }
}
