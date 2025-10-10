    package com.example.renault.controllers;

    import com.example.renault.dto.GarageDTO;
    import com.example.renault.entities.GarageEntity;
    import com.example.renault.enums.FuelTypeEnum;
    import com.example.renault.mapper.GarageMapper;
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
        private final GarageMapper garageMapper;

        public GarageController(GarageService garageService,
                                GarageMapper garageMapper){
            this.garageService = garageService;
            this.garageMapper = garageMapper;
        }

        @PostMapping
        public ResponseEntity<?> create(@RequestBody GarageDTO garage){
            try {
                GarageEntity created = garageService.create(garageMapper.toEntity(garage));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(garageMapper.toDTO(created));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<GarageDTO> update(@PathVariable Long id, @RequestBody GarageDTO garage){
            return garageService.update(id, garageMapper.toEntity(garage))
                    .map(updated -> ResponseEntity.ok(garageMapper.toDTO(updated)))
                    .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id){
            garageService.delete(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/{id}")
        public ResponseEntity<GarageDTO> searchById(@PathVariable Long id){
            return garageService.findById(id)
                    .map(found -> ResponseEntity.ok(garageMapper.toDTO(found)))
                    .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping
        public ResponseEntity<Page<GarageDTO>> searchPaginatedSorted(Pageable pageable){
            Page<GarageDTO> result = garageService.findAll(pageable)
                    .map(garageMapper::toDTO);
            return ResponseEntity.ok(result);
        }

        @GetMapping("/vehicule/{fuelType}")
        public ResponseEntity<List<GarageDTO>> searchByVehiculeType(@PathVariable FuelTypeEnum fuelType){
            List<GarageDTO> result = garageService.findGarageByVehiculeType(fuelType)
                    .stream().map(garageMapper::toDTO).toList();
            return ResponseEntity.ok(result);
        }

        @GetMapping("/accessory/{accessoryName}")
        public ResponseEntity<List<GarageDTO>> searchByVehiculeAccessory(@PathVariable String accessoryName){
            List<GarageDTO> result = garageService.findGaragesByAccessory(accessoryName)
                    .stream().map(garageMapper::toDTO).toList();
            return ResponseEntity.ok(result);
        }
    }
