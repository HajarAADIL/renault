package com.example.renault.services;

import com.example.renault.entities.GarageEntity;
import com.example.renault.entities.VehiculeEntity;
import com.example.renault.enums.FuelTypeEnum;
import com.example.renault.repositories.GarageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GarageService {

    private final GarageRepository garageRepository;

    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public GarageEntity create(GarageEntity garage) {
        return garageRepository.save(garage);
    }

    public Optional<GarageEntity> update(Long id, GarageEntity garage) {
        return garageRepository.findById(id)
                .map(old -> {
                    old.setName(garage.getName());
                    old.setAddress(garage.getAddress());
                    old.setEmail(garage.getEmail());
                    old.setPhone(garage.getPhone());
                    old.setVehicules(garage.getVehicules());
                    old.getOpeningTimes().clear();

                    if (garage.getOpeningTimes() != null) {
                        garage.getOpeningTimes().forEach(old::addOpening);
                    }

                    return garageRepository.save(old);
                });
    }

    public void delete(Long id) {
        GarageEntity garage = garageRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Garage not found with id " + id));
        garageRepository.delete(garage);
    }

    public Optional<GarageEntity> findById(Long id){
        return garageRepository.findById(id);
    }

    public Page<GarageEntity> findAll(Pageable pageable) {
        return garageRepository.findAll(pageable);
    }

    public List<GarageEntity> findGarageByVehiculeType(FuelTypeEnum fuelType){
        return garageRepository.findByFuelType(fuelType);
    }

    public List<GarageEntity> findGaragesByAccessory(String accessoryName){
        return garageRepository.findByAccessoryId(accessoryName);
    }


}
