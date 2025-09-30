package com.example.renault.controllers;

import com.example.renault.entities.Accessory;
import com.example.renault.services.AccessoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accessories")
public class AccessoryController {
    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService){
        this.accessoryService = accessoryService;
    }

    @PostMapping
    public ResponseEntity<Accessory> create(@RequestBody Accessory accessory){
        Accessory created = accessoryService.create(accessory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accessory> update(@PathVariable Long id, @RequestBody Accessory accessory){
        return accessoryService.update(id, accessory)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return accessoryService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/vehicule/{id}")
    public ResponseEntity<List<Accessory>> searchByVehicule(@PathVariable Long id){
        return ResponseEntity.ok(accessoryService.findByVehiculeId(id));
    }

}
