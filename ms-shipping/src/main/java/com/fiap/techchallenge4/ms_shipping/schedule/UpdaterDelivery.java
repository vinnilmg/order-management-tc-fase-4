package com.fiap.techchallenge4.ms_shipping.schedule;

import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.controller.request.DeliveryUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import com.fiap.techchallenge4.ms_shipping.service.CourierService;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import com.fiap.techchallenge4.ms_shipping.service.ShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(name = "job.courier.enabled", havingValue = "true", matchIfMissing = false)
public class UpdaterDelivery {

    private final DeliveryService deliveryService;
    private final CourierService courierService;
    private final ShippingService shippingService;
    private final DeliveryRepository deliveryRepository;

    public UpdaterDelivery(DeliveryService deliveryService, CourierService courierService, ShippingService shippingService, DeliveryRepository deliveryRepository) {
        this.deliveryService = deliveryService;
        this.courierService = courierService;
        this.shippingService = shippingService;
        this.deliveryRepository = deliveryRepository;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkDelivery() {
        List<ShippingEntity> shipping = this.shippingService.getAllRegion();

        for (ShippingEntity s:shipping){
            //System.out.println("a região atual é "+ s.name());
            log.info("a região atual é {}", s.getId());

            //Pegando os pedidos da região atual
            List<DeliveryEntity> listDelivery = this.deliveryService.findByStatusAndRegionId(ShippingStatusEnum.WAITING_FOR_DELIVERY, s.getId());
            if (listDelivery.isEmpty()) {
                //system.out.println("não achou pedido para região "+ s.getId());
                log.info("não achou pedido para região {}", s.getId());
            } else {
                //LISTA DE PEDIDOS ENCONTRADOS
                //System.out.println("pedido encontrado para região "+ s.name() +": " +delivery);

                //Se achou pedido na região procura o entregador para região
                List<CourierEntity> couriers = this.courierService.findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE,s.getId());

                if (couriers.isEmpty()) {
                    //System.out.println("entregador não disponível para região "+ s.getId());
                    log.info("entregador não disponível para região {}", s.getId());
                }else {
                    CourierEntity courier = couriers.stream().findFirst().orElse(null);;
                    //System.out.println("entregador encontrado na região "+ s.getId() + ": " + courier.getId());
                    log.info("entregador encontrado na região {}", courier.getId());

                    //ATUALIZAR O STATUS DO ENTREGADOR PARA EM ROTA(AvaialableCourierEnum.ON_DELIVERY_ROUTE)
                    CourierUpdateRequest req = new CourierUpdateRequest(AvaialableCourierEnum.ON_DELIVERY_ROUTE);
                    this.courierService.updateStatus(courier.getId(), req);

                    courier.setStatus(AvaialableCourierEnum.ON_DELIVERY_ROUTE);

                    for (DeliveryEntity delivery: listDelivery) {
                        //System.out.println("id do pedido atual: " + delivery.getId());
                        log.info("id do pedido atual sendo atualizado {}", delivery.getId());
                        //ATUALIZAR O LIST DE PEDIDO COM NÚMERO DO ENTREGADOR E STATUS PARA EM ROTA(ShippingStatusEnum.ON_DELIVERY_ROUTE)
                        DeliveryUpdateRequest reqDelivery = new DeliveryUpdateRequest(ShippingStatusEnum.ON_DELIVERY_ROUTE, courier.getId());
                        //this.deliveryService.updateStatus(delivery.getId(), reqDelivery);
                        delivery.setCourier(courier);
                        delivery.setStatus(ShippingStatusEnum.ON_DELIVERY_ROUTE);
                        deliveryRepository.save(delivery);
                    }

                }
            }
        }

    }
}
