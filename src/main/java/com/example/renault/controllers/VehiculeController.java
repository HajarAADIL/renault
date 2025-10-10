package com.example.renault.controllers;

import com.example.renault.dto.VehiculeDTO;
import com.example.renault.entities.GarageEntity;
import com.example.renault.entities.VehiculeEntity;
import com.example.renault.mapper.VehiculeMapper;
import com.example.renault.services.VehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;


    public VehiculeController(VehiculeService vehiculeService,
                              VehiculeMapper vehiculeMapper){
        this.vehiculeService = vehiculeService;
        this.vehiculeMapper = vehiculeMapper;
    }

    @PostMapping
    public ResponseEntity<VehiculeDTO> create(@RequestBody VehiculeDTO vehicule){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehiculeService.create(vehicule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> update(@PathVariable Long id, @RequestBody VehiculeDTO vehicule){
        return vehiculeService.update(id, vehicule)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        vehiculeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/garage/{id}")
    public ResponseEntity<List<VehiculeDTO>> searchByGarage(@PathVariable Long id){
        List<VehiculeDTO> result = vehiculeService.findByGarageId(id)
                .stream().map(vehiculeMapper::toDTO).toList();
        return ResponseEntity.ok(result);

    }

    @GetMapping("/brand/{brand}/group-by-garage")
    public ResponseEntity<Map<Long, List<VehiculeDTO>>> getVehiculesByBrandGrouped(@PathVariable String brand) {
        try {
            Map<GarageEntity, List<VehiculeEntity>> result =
                    vehiculeService.findVehiculeByModelGroupByGarage(brand);

            Map<Long, List<VehiculeDTO>> dtoResult = result.entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey().getId(),
                            entry -> entry.getValue().stream()
                                    .map(vehiculeMapper::toDTO)
                                    .toList()
                    ));

            return ResponseEntity.ok(dtoResult);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
