package maybe_all_here.orderservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.service.util.PriceCalculator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemTitle;
    private long orderQuantity;
    private long totalPrice;
    private long discountedPrice;
    private long spentMileage;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private Long itemId;

    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    @Builder
    public Orders(Long id, String itemTitle, long orderQuantity, long totalPrice, long discountedPrice, long spentMileage, String email, Long itemId, OrderState orderState) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.orderQuantity = orderQuantity;
        this.totalPrice = totalPrice;
        this.discountedPrice = discountedPrice;
        this.spentMileage = spentMileage;
        this.email = email;
        this.itemId = itemId;
        this.orderState = orderState;
    }

    public void order(OrderRequest orderRequest, ItemProvideResponse item, String email) {
        long totalPrice = PriceCalculator.calculateTotalPrice(
                item.getItemPrice(),
                orderRequest.getOrderQuantity()
        );

        long discountedPrice = PriceCalculator.calculateDiscountedPrice(
                totalPrice, orderRequest.getSpentMileage()
        );

        this.itemTitle = item.getTitle();
        this.orderQuantity = orderRequest.getOrderQuantity();
        this.totalPrice = totalPrice;
        this.discountedPrice = discountedPrice;
        this.spentMileage = orderRequest.getSpentMileage();
        this.email = email;
        this.itemId = item.getId();
        this.orderState = OrderState.ORDER;
    }

    public void cancel() {
        this.orderState = OrderState.CANCEL;
    }
}
