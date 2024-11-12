package com.fiap.techchallenge4.ms_shipping.schedule;

import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.controller.request.DeliveryUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import com.fiap.techchallenge4.ms_shipping.service.CourierService;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import com.fiap.techchallenge4.ms_shipping.service.ShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UpdaterDelivery {

    private final DeliveryService deliveryService;
    private final CourierService courierService;
    private final ShippingService shippingService;

    public UpdaterDelivery(DeliveryService deliveryService, CourierService courierService, ShippingService shippingService) {
        this.deliveryService = deliveryService;
        this.courierService = courierService;
        this.shippingService = shippingService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkDelivery() {
        List<ShippingEntity> shipping = this.shippingService.getAllRegion();

        if (!shipping.isEmpty()) {
            List<RegionEnum> region = shipping
                    .stream()
                    .map( e -> e.getRegion())
                    .distinct()
                    .collect(Collectors.toList());

            for (RegionEnum s:region){
                //System.out.println("a região atual é "+ s.name());
                log.info("a região atual é {}", s.name());

                //Pegando os pedidos da região atual
                List<DeliveryEntity> listDelivery = this.deliveryService.findByStatusAndRegion(ShippingStatusEnum.WAITING_FOR_DELIVERY, s);
                if (listDelivery.isEmpty()) {
                    System.out.println("não achou pedido para região "+ s.name());
                } else {
                    //LISTA DE PEDIDOS ENCONTRADOS
                    //System.out.println("pedido encontrado para região "+ s.name() +": " +delivery);

                    //Se achou pedido na região procura o entregador para região
                    List<CourierEntity> couriers = this.courierService.findByStatusAndRegion(AvaialableCourierEnum.AVAILABLE,s);

                    if (couriers.isEmpty()) {
                        System.out.println("entregador não disponível para região "+ s.name());
                    }else {
                        CourierEntity courier = couriers.stream().findFirst().orElse(null);;
                        System.out.println("entregador encontrado na região "+ s.name() + ": " + courier.getId());

                        //ATUALIZAR O STATUS DO ENTREGADOR PARA EM ROTA(AvaialableCourierEnum.ON_DELIVERY_ROUTE)
                        CourierUpdateRequest req = new CourierUpdateRequest(AvaialableCourierEnum.ON_DELIVERY_ROUTE);
                        this.courierService.updateStatus(courier.getId(), req);

                        courier.setStatus(AvaialableCourierEnum.ON_DELIVERY_ROUTE);

                        for (DeliveryEntity delivery: listDelivery) {
                            System.out.println("id do pedido atual: " + delivery.getId());
                            //ATUALIZAR O LIST DE PEDIDO COM NÚMERO DO ENTREGADOR E STATUS PARA EM ROTA(ShippingStatusEnum.ON_DELIVERY_ROUTE)
                            DeliveryUpdateRequest reqDelivery = new DeliveryUpdateRequest(ShippingStatusEnum.ON_DELIVERY_ROUTE, courier.getId());
                            this.deliveryService.updateStatus(delivery.getId(), reqDelivery);
                        }

                    }
                }
            }



        }
    }
}
