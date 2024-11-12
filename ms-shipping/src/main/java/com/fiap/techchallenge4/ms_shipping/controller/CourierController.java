package com.fiap.techchallenge4.ms_shipping.controller;

import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api")
public class CourierController {

    @Autowired
    private CourierService courierService;

    @GetMapping("courier")
    public ResponseEntity<Collection<CourierEntity>> findAll() {
        var carros = courierService.findAll();
        return ResponseEntity.ok(carros);
    }

    @GetMapping("courier/{id}")
    public ResponseEntity<Optional<CourierEntity>> findById(@PathVariable String id) {
        var courier = courierService.findById(parseLong(id));
        return ResponseEntity.ok(courier);
    }

    @PostMapping("courier")
    public ResponseEntity<CourierEntity> save(@RequestBody CourierEntity courier) {
        courier = courierService.save(courier);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(courier);
    }

    @PutMapping("courier/updatestatus/{id}")
    public ResponseEntity<CourierEntity> updateStatus(@PathVariable Long id, @RequestBody CourierUpdateRequest courier) {
        return ResponseEntity.ok(courierService.updateStatus(id, courier));
    }

    @GetMapping("courier-available/{region}")
    public ResponseEntity<List<CourierEntity>> findByStatusAndRegion(@PathVariable RegionEnum region) {
        var courier = courierService.findByStatusAndRegion(AvaialableCourierEnum.AVAILABLE, region);
        return ResponseEntity.ok(courier);
    }


}
