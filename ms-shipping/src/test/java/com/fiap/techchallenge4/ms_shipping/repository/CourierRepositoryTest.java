package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourierRepositoryTest {

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private ShippingRepository shippingRepository; // Para salvar ShippingEntity

    @Test
    @DisplayName("Deve encontrar couriers pelo status e região")
    void testFindByStatusAndRegionId() {
        // Arrange
        ShippingEntity region1 = new ShippingEntity();
        region1.setRegion(RegionEnum.NORTE);
        region1.setCepStart("12345678");
        region1.setCepEnd("87654321");
        region1.setDaysToDelivery("3");
        region1.setDeliveryPrice(BigDecimal.valueOf(50.00));
        shippingRepository.save(region1);

        ShippingEntity region2 = new ShippingEntity();
        region2.setRegion(RegionEnum.SUL);
        region2.setCepStart("22222222");
        region2.setCepEnd("33333333");
        region2.setDaysToDelivery("5");
        region2.setDeliveryPrice(BigDecimal.valueOf(70.00));
        shippingRepository.save(region2);

        CourierEntity courier1 = new CourierEntity();
        courier1.setName("Courier 1");
        courier1.setStatus(AvaialableCourierEnum.AVAILABLE);
        courier1.setPhone("1122225555");
        courier1.setRegion(region1); // Associando região
        courierRepository.save(courier1);

        CourierEntity courier2 = new CourierEntity();
        courier2.setName("Courier 2");
        courier2.setStatus(AvaialableCourierEnum.UNAVAILABLE);
        courier2.setPhone("11233335555");
        courier2.setRegion(region1);
        courierRepository.save(courier2);

        CourierEntity courier3 = new CourierEntity();
        courier3.setName("Courier 3");
        courier3.setStatus(AvaialableCourierEnum.AVAILABLE);
        courier3.setRegion(region2);
        courier3.setPhone("11444445555");
        courierRepository.save(courier3);

        //courierRepository.saveAll(List.of(courier1, courier2, courier3));

        // Act
        List<CourierEntity> result = courierRepository.findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, region1.getId());

        // Assert
        assertEquals(1, result.size());
        assertEquals("Courier 1", result.get(0).getName());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum courier corresponder")
    void testFindByStatusAndRegionId_NoResults() {
        // Arrange
        ShippingEntity region = new ShippingEntity();
        region.setRegion(RegionEnum.CENTRO_OESTE);
        region.setCepStart("44444444");
        region.setCepEnd("55555555");
        region.setDaysToDelivery("4");
        region.setDeliveryPrice(BigDecimal.valueOf(60.00));
        shippingRepository.save(region);

        CourierEntity courier = new CourierEntity();
        courier.setName("Courier 1");
        courier.setStatus(AvaialableCourierEnum.AVAILABLE);
        courier.setRegion(region);
        courier.setPhone("1122225555");
        courierRepository.save(courier);

        // Act
        List<CourierEntity> result = courierRepository.findByStatusAndRegionId(AvaialableCourierEnum.UNAVAILABLE, region.getId());

        // Assert
        assertTrue(result.isEmpty());
    }
}
