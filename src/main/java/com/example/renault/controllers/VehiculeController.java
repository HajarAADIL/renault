package com.example.renault.controllers;

import com.example.renault.entities.Garage;
import com.example.renault.entities.Vehicule;
import com.example.renault.services.VehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService){
        this.vehiculeService = vehiculeService;
    }

    @PostMapping
    public ResponseEntity<Vehicule> create(@RequestBody Vehicule vehicule){
        Vehicule created = vehiculeService.create(vehicule);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> update(@PathVariable Long id, @RequestBody Vehicule vehicule){
        return vehiculeService.update(id, vehicule)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return vehiculeService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/garage/{id}")
    public ResponseEntity<List<Vehicule>> searchByGarage(@PathVariable Long id){
        return ResponseEntity.ok(vehiculeService.findByGarageId(id));
    }

    @GetMapping("/brand/{brand}/group-by-garage")
    public ResponseEntity<Map<Garage, List<Vehicule>>> getVehiculesByBrandGrouped(@PathVariable String brand) {
        try {
            Map<Garage, List<Vehicule>> result =
                    vehiculeService.findVehiculeByModelGroupByGarage(brand);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
