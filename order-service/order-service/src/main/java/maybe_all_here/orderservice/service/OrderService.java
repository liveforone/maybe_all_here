package maybe_all_here.orderservice.service;

import lombok.RequiredArgsConstructor;
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
        OrderRequest finalRequest = OrderMapper.dtoToCalculatedDto(orderRequest, item, email);

        if (finalRequest.getSpentMileage() != ZERO) {
            orderProducer.decreaseMileage(finalRequest);
        }
        orderProducer.increaseMileage(finalRequest);
        orderProducer.decreaseRemaining(finalRequest);

        return orderRepository
                .save(OrderMapper.dtoToEntity(finalRequest))
                .getId();
    }
}
