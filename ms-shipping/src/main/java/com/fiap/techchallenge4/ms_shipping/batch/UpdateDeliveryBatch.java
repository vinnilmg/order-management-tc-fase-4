package com.fiap.techchallenge4.ms_shipping.batch;

import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import com.fiap.techchallenge4.ms_shipping.service.CourierService;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import com.fiap.techchallenge4.ms_shipping.service.ShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnProperty(name = "job.courier.enabled", havingValue = "true", matchIfMissing = false)
public class UpdateDeliveryBatch {

    private final DeliveryService deliveryService;
    private final CourierService courierService;
    private final ShippingService shippingService;
    private final DeliveryRepository deliveryRepository;

    public UpdateDeliveryBatch(DeliveryService deliveryService, CourierService courierService, ShippingService shippingService, DeliveryRepository deliveryRepository) {
        this.deliveryService = deliveryService;
        this.courierService = courierService;
        this.shippingService = shippingService;
        this.deliveryRepository = deliveryRepository;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkDelivery() {
        List<ShippingEntity> shipping = this.shippingService.getAllRegion();

        for (ShippingEntity s : shipping) {
            log.info("a região atual é {}", s.getId());

            List<DeliveryEntity> listDelivery = this.deliveryService.findByStatusAndRegionId(DeliveryStatusEnum.WAITING_FOR_DELIVERY, s.getId());
            if (listDelivery.isEmpty()) {
                log.info("não achou pedido para região {}", s.getId());
            } else {
                List<CourierEntity> couriers = this.courierService.findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, s.getId());

                if (couriers.isEmpty()) {
                    log.info("entregador não disponível para região {}", s.getId());
                } else {
                    CourierEntity courier = couriers.stream().findFirst().orElse(null);
                    log.info("entregador encontrado na região {}", courier.getId());
                    CourierUpdateRequest req = new CourierUpdateRequest(AvaialableCourierEnum.ON_DELIVERY_ROUTE);
                    this.courierService.updateStatus(courier.getId(), req);

                    courier.setStatus(AvaialableCourierEnum.ON_DELIVERY_ROUTE);

                    for (DeliveryEntity delivery : listDelivery) {
                        log.info("id do pedido atual sendo atualizado {}", delivery.getId());
                        delivery.setCourier(courier);
                        delivery.setStatus(DeliveryStatusEnum.ON_DELIVERY_ROUTE);
                        deliveryRepository.save(delivery);
                        log.info("calling ms-order to change order id: {} to ON_DELIVERY_ROUTE", delivery.getOrderId());
                        courierService.updateOrderToDeliveryRoute(delivery.getOrderId());
                    }
                }
            }
        }
    }
}
