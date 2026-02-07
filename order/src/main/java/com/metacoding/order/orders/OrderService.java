package com.metacoding.order.orders;

import com.metacoding.order.core.handler.ex.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponse createOrder(int userId, List<OrderRequest.OrderItemDTO> orderItems, String address) {
        // 1. 주문 생성 및 저장
        Order createdOrder = orderRepository.save(Order.create(userId));
        
        // 2. 주문 아이템 생성 및 저장 
        orderItemRepository.saveAll(
            orderItems.stream()
                .map(item -> OrderItem.create(createdOrder.getId(), item.productId(), item.quantity(), item.price()))
                .toList()
        );
        // 3. 주문 완료 처리
        createdOrder.complete();
        return OrderResponse.from(createdOrder);
    }

    public OrderResponse findById(int orderId) {
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다."));
        return OrderResponse.from(findOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(int orderId) {
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다."));
        findOrder.cancel(); // 더티 체킹으로 상태 저장
        return OrderResponse.from(findOrder);
    }
}