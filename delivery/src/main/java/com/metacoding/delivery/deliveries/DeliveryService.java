package com.metacoding.delivery.deliveries;

import com.metacoding.delivery.core.handler.ex.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public DeliveryResponse createDelivery(int orderId, String address) {
        Delivery savedDelivery = Delivery.create(orderId, address);
        deliveryRepository.save(savedDelivery);
        savedDelivery.complete();
        return DeliveryResponse.from(savedDelivery);
    }

    public DeliveryResponse findById(int deliveryId) {
        Delivery findDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception404("배달 정보를 조회할 수 없습니다."));
        return DeliveryResponse.from(findDelivery);
    }

    @Transactional
    public void cancelDelivery(int orderId) {
        Delivery cancelledDelivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new Exception404("배달 정보를 조회할 수 없습니다."));
        cancelledDelivery.cancel();
    }
}