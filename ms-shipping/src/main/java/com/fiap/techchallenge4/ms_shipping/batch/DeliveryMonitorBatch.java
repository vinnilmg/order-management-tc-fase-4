package com.fiap.techchallenge4.ms_shipping.batch;

import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.CourierRepository;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "job.delivery.enabled", havingValue = "true", matchIfMissing = false)
public class DeliveryMonitorBatch {

    private final DeliveryRepository deliveryRepository;
    private final CourierRepository courierRepository;
    private final DeliveryService deliveryService;

    public DeliveryMonitorBatch(DeliveryRepository deliveryRepository, CourierRepository courierRepository, DeliveryService deliveryService) {
        this.deliveryRepository = deliveryRepository;
        this.courierRepository = courierRepository;
        this.deliveryService = deliveryService;
    }

    @Scheduled(fixedRate = 30000, initialDelay = 1500)
    @Transactional
    public void updateDeliveriesInTransit(){

        log.info("Initiating delivery monitoring batch");
        // Find all deliveries in transit
        var count = deliveryRepository.countByStatusAndCourierIsNotNull(DeliveryStatusEnum.ON_DELIVERY_ROUTE);
        log.info("Found {} deliveries in transit", count);

        // Update the Long and Lat of all deliveries in transit
        deliveryRepository.findByStatusAndCourierIsNotNull(DeliveryStatusEnum.ON_DELIVERY_ROUTE).forEach(delivery ->{
            if(delivery.getLatitude() >=0 && delivery.getLatitude() <=9 && delivery.getLongitude() >=0 && delivery.getLongitude() <=9){
                delivery.setLatitude(delivery.getLatitude() + 1);
                delivery.setLongitude(delivery.getLongitude() + 1);
                deliveryRepository.save(delivery);
                log.info("Updating delivery id: {}", delivery.getId());
            }
            if(delivery.getLatitude() >=10 && delivery.getLongitude() >=10){
                delivery.setStatus(DeliveryStatusEnum.DELIVERED);
                deliveryRepository.save(delivery);
                log.info("Delivery id: {} has been delivered, calling ms-order to change order to DELIVERED", delivery.getId());
                courierRepository.getReferenceById(delivery.getCourier().getId()).setStatus(AvaialableCourierEnum.AVAILABLE);
                log.info("Courier id: {} is now available", delivery.getCourier().getId());
                deliveryService.finishOrder(delivery.getOrderId());
            }
        });
    }
}
