package com.example.renault.services;

import com.example.renault.entities.Garage;
import com.example.renault.enums.FuelType;
import com.example.renault.repositories.GarageRepository;
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

    public Garage update(Garage garage) {
        return garageRepository.findById(garage.getId())
                .map(old -> {
                    old.setName(garage.getName());
                    old.setAddress(garage.getAddress());
                    old.setEmail(garage.getEmail());
                    old.setPhone(garage.getPhone());
                    old.setHoraires(garage.getHoraires());
                    old.setVehicules(garage.getVehicules());

                    return garageRepository.save(old);
                })
                .orElseThrow(() -> new IllegalArgumentException("garage not found"));
    }

    public void delete(Long id) {
        Optional<Garage> found = garageRepository.findById(id);

        if(found.isEmpty())
            throw  new IllegalArgumentException("garage not found");

        garageRepository.deleteById(id);
    }

    public Garage findById(Long id){
        return garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("garage not found"));
    }

    public List<Garage> findAll() {
        return garageRepository.findAll();
    }

    public List<Garage> findGarageByVehiculeType(FuelType fuelType){
        return garageRepository.findByFuelType(fuelType.name());
    }

    public List<Garage> findGaragesByAccessory(Long accessoryId){
        return garageRepository.findByAccessoryId(accessoryId);
    }


}
