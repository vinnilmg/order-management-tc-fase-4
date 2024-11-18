package com.fiap.techchallenge4.ms_shipping.batch;

import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.CourierRepository;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "job.enabled", havingValue = "true", matchIfMissing = false)
public class DeliveryMonitorBatch {

    private final DeliveryRepository deliveryRepository;
    private final CourierRepository courierRepository;

    public DeliveryMonitorBatch(DeliveryRepository deliveryRepository, CourierRepository courierRepository) {
        this.deliveryRepository = deliveryRepository;
        this.courierRepository = courierRepository;
    }

    @Scheduled(fixedRate = 3000, initialDelay = 1500)
    @Transactional
    public void updateDeliveriesInTransit(){

        log.info("Initiating delivery monitoring batch");
        // Find all deliveries in transit
        var count = deliveryRepository.countByStatusAndCourierIsNotNull(DeliveryStatusEnum.ON_DELIVERY_ROUTE);
        log.info("Found {} deliveries in transit", count);

        // Update the Long and Lat of all deliveries in transit
        deliveryRepository.findByStatusAndCourierIsNotNull(DeliveryStatusEnum.ON_DELIVERY_ROUTE).stream().forEach(delivery ->{
            if(delivery.getLatitude() >=0 && delivery.getLatitude() <=9 && delivery.getLongitude() >=0 && delivery.getLongitude() <=9){
                delivery.setLatitude(delivery.getLatitude() + 1);
                delivery.setLongitude(delivery.getLongitude() + 1);
                deliveryRepository.save(delivery);
                log.info("Updating delivery id: {}", delivery.getId());
            }
            if(delivery.getLatitude() >=10 && delivery.getLongitude() >=10){
                delivery.setStatus(DeliveryStatusEnum.DELIVERED);
                deliveryRepository.save(delivery);
                log.info("Delivery id: {} has been delivered", delivery.getId());
                courierRepository.getReferenceById(delivery.getCourier().getId()).setStatus(AvaialableCourierEnum.AVAILABLE);
                log.info("Courier id: {} is now available", delivery.getCourier().getId());
            }
//            TODO: Ao inves de alterar o status do entregador para disponivel
        });
    }
}
