package maybe_all_here.orderservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.domain.OrderState;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.kafka.OrderProducer;
import maybe_all_here.orderservice.repository.OrderRepository;
import maybe_all_here.orderservice.service.util.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    private static final int ZERO = 0;

    public OrderProvideResponse getOrderByEmailAndItemId(String email, Long itemId) {
        return OrderMapper.entityToProvideDto(
                orderRepository.findOneByEmailAndItemId(email, itemId)
        );
    }

    @Transactional
    public Long order(
            OrderRequest orderRequest, ItemProvideResponse item, String email
    ) {
        OrderRequest finalRequest = OrderRequest.builder()
                .itemTitle(item.getTitle())
                .orderQuantity(orderRequest.getOrderQuantity())
                .totalPrice(calculateTotalPrice(
                        item.getItemPrice(),
                        orderRequest.getOrderQuantity()
                ))
                .discountPrice(orderRequest.getSpentMileage())
                .spentMileage(orderRequest.getSpentMileage())
                .email(email)
                .itemId(item.getId())
                .orderState(OrderState.ORDER)
                .build();

        if (finalRequest.getSpentMileage() != ZERO) {
            orderProducer.decreaseMileage(finalRequest);
        }
        orderProducer.increaseMileage(finalRequest);
        orderProducer.decreaseRemaining(finalRequest);

        return orderRepository
                .save(OrderMapper.dtoToEntity(finalRequest))
                .getId();
    }

    private long calculateTotalPrice(long itemPrice, long orderQuantity) {
        return itemPrice * orderQuantity;
    }
}
