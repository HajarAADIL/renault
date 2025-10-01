package com.example.renault.services;

import com.example.renault.dto.VehiculeDTO;
import com.example.renault.entities.Garage;
import com.example.renault.entities.Vehicule;
import com.example.renault.mapper.VehiculeMapper;
import com.example.renault.repositories.GarageRepository;
import com.example.renault.repositories.VehiculeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehiculeService {

    public static final int MAX_VEHICULE = 50;

    private final VehiculeRepository vehiculeRepository;
    private final GarageRepository garageRepository;
    private final VehiculeMapper vehiculeMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public VehiculeService(VehiculeRepository vehiculeRepository,
                           GarageRepository garageRepository,
                           VehiculeMapper vehiculeMapper,
                           KafkaTemplate<String, String> kafkaTemplate) {
        this.vehiculeRepository = vehiculeRepository;
        this.garageRepository = garageRepository;
        this.vehiculeMapper = vehiculeMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public VehiculeDTO create(VehiculeDTO vehiculeDto) {
        Garage garage = garageRepository.findById(vehiculeDto.garageId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));

        //RG: Chaque garage peut stocker au maximum 50 véhicules.
        long vehiculeNumber = vehiculeRepository.countByGarageId(garage.getId());
        if(vehiculeNumber >= MAX_VEHICULE)
            throw new IllegalArgumentException(
                    String.format("The garage %s has reached the maximum number of vehicles.", garage.getName()));


        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDto);
        vehicule.setGarage(garage);

        //Save
        Vehicule created = vehiculeRepository.save(vehicule);

        // Publish the event to Kafka (simplified JSON)
        String eventMessage = String.format("{\"id\":%d,\"model\":\"%s\",\"brand\":\"%s\",\"type\":%s,\"garageId\":%d}",
                created.getId(), created.getFabricationDate(), created.getBrand(), created.getFuelType().name(), created.getGarage().getId());

        kafkaTemplate.send("vehicule-created", eventMessage);

        return vehiculeMapper.toDTO(created);

    }

    public Optional<VehiculeDTO> update(Long id, VehiculeDTO vehicule) {
        Garage garage = garageRepository.findById(vehicule.garageId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found"));


        return vehiculeRepository.findById(id)
                .map(old -> {
                    old.setBrand(vehicule.brand());
                    old.setFabricationDate(vehicule.fabricationDate());
                    old.setFuelType(vehicule.fuelType());
                    old.setGarage(garage);

                    return vehiculeMapper.toDTO(vehiculeRepository.save(old));
                });
    }

    public boolean delete(Long id) {
        if (vehiculeRepository.existsById(id)) {
            vehiculeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Vehicule> findByGarageId(Long garageId) {
        return vehiculeRepository.findByGarageId(garageId);
    }

    public Map<Garage, List<Vehicule>> findVehiculeByModelGroupByGarage(String brand){
        List<Vehicule> vehicules = vehiculeRepository.findByBrand(brand);

        if(CollectionUtils.isEmpty(vehicules))
            throw  new IllegalArgumentException(String.format("Vehicules with brand %s not found", brand));

        return vehicules.stream().collect(Collectors.groupingBy(Vehicule::getGarage));
    }
}
