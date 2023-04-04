package maybe_all_here.orderservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.orderservice.kafka.OrderProducer;
import maybe_all_here.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
}
