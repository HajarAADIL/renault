package com.example.renault.controllers;

import com.example.renault.entities.Garage;
import com.example.renault.enums.FuelType;
import com.example.renault.services.GarageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/garages")
public class GarageController {

    private final GarageService garageService;

    public GarageController(GarageService garageService){
        this.garageService = garageService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Garage garage){
        try {
            Garage created = garageService.create(garage);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Garage> update(@PathVariable Long id, @RequestBody Garage garage){
        return garageService.update(id, garage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return garageService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garage> searchById(@PathVariable Long id){
        return garageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Garage>> searchPaginatedSorted(Pageable pageable){
        return ResponseEntity.ok(garageService.findAll(pageable));
    }

    @GetMapping("/vehicule/{fuelType}")
    public ResponseEntity<List<Garage>> searchByVehiculeType(@PathVariable FuelType fuelType){
        return ResponseEntity.ok(garageService.findGarageByVehiculeType(fuelType));
    }

    @GetMapping("/accessory/{id}")
    public ResponseEntity<List<Garage>> searchByVehiculeType(@PathVariable Long accessoryId){
        return ResponseEntity.ok(garageService.findGaragesByAccessory(accessoryId));
    }
}
