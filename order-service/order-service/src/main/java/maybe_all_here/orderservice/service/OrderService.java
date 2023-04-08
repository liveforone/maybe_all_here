package maybe_all_here.orderservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.domain.Orders;
import maybe_all_here.orderservice.dto.item.ItemProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderProvideResponse;
import maybe_all_here.orderservice.dto.order.OrderRequest;
import maybe_all_here.orderservice.dto.order.OrderResponse;
import maybe_all_here.orderservice.kafka.OrderProducer;
import maybe_all_here.orderservice.repository.OrderRepository;
import maybe_all_here.orderservice.service.util.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    private static final int ZERO = 0;

    public OrderProvideResponse getProvideOrderById(Long orderId) {
        return OrderMapper.entityToProvideDto(
                orderRepository.findOneForProvideById(orderId)
        );
    }

    public List<OrderResponse> getOrdersByEmail(String email, Long lastId, int pageSize) {
        return OrderMapper.entityToDtoList(
                orderRepository.findOrdersByEmail(email, lastId, pageSize)
        );
    }

    public OrderResponse getOrderById(Long orderId) {
        return OrderMapper.entityToDto(
                orderRepository.findOneById(orderId)
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

    @Transactional
    public void cancelOrder(Long orderId) {
        orderProducer.removeReviewBelongOrder(orderId);
        orderRepository.cancelOneById(orderId);
    }
}
