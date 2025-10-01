package com.example.renault.services;

import com.example.renault.entities.Garage;
import com.example.renault.enums.FuelType;
import com.example.renault.repositories.GarageRepository;
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

    public Garage create(Garage garage) {
        return garageRepository.save(garage);
    }

    public Optional<Garage> update(Long id, Garage garage) {
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

    public boolean delete(Long id) {
        if (garageRepository.existsById(id)) {
            garageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Garage> findById(Long id){
        return garageRepository.findById(id);
    }

    public Page<Garage> findAll(Pageable pageable) {
        return garageRepository.findAll(pageable);
    }

    public List<Garage> findGarageByVehiculeType(FuelType fuelType){
        return garageRepository.findByFuelType(fuelType);
    }

    public List<Garage> findGaragesByAccessory(String accessoryName){
        return garageRepository.findByAccessoryId(accessoryName);
    }


}
