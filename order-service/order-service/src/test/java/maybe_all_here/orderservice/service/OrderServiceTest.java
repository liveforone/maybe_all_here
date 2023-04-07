package maybe_all_here.orderservice.service;

import jakarta.persistence.EntityManager;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void orderTest() {
        //given
        long orderQuantity = 2;
        long itemPrice = 20000;
        long mileage = 5000;
        String email = "aaaa1234@gmail.com";
        OrderRequest orderRequest = OrderRequest.builder()
                .orderQuantity(orderQuantity)
                .spentMileage(mileage)
                .build();
        ItemProvideResponse item = ItemProvideResponse.builder()
                .id(1L)
                .title("test_item_title")
                .itemPrice(itemPrice)
                .remaining(20)
                .build();

        //when
        Long orderId = orderService.order(orderRequest, item, email);
        em.flush();
        em.clear();

        //then
        long totalPrice = 40000;
        long finalPrice = 35000;
        Assertions
                .assertThat(orderService.getOrderById(orderId).getTotalPrice())
                .isEqualTo(totalPrice);

        Assertions
                .assertThat(orderService.getOrderById(orderId).getDiscountedPrice())
                .isEqualTo(finalPrice);
    }

    @Test
    @Transactional
    void cancelOrderTest() {
        //given
        long orderQuantity = 1;
        long itemPrice = 20000;
        long mileage = 0;
        String email = "aaaa1234@gmail.com";
        OrderRequest orderRequest = OrderRequest.builder()
                .orderQuantity(orderQuantity)
                .spentMileage(mileage)
                .build();
        ItemProvideResponse item = ItemProvideResponse.builder()
                .id(2L)
                .title("test_item_title2")
                .itemPrice(itemPrice)
                .remaining(100)
                .build();
        Long orderId = orderService.order(orderRequest, item, email);
        em.flush();
        em.clear();

        //when
        orderService.cancelOrder(orderId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(orderService.getOrderById(orderId).getId())
                .isNull();
    }
}