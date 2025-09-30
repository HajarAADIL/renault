package com.example.renault.services;

import com.example.renault.entities.Garage;
import com.example.renault.entities.Vehicule;
import com.example.renault.repositories.VehiculeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculeService {
    
    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public Vehicule create(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    public Vehicule update(Vehicule vehicule) {
        return vehiculeRepository.findById(vehicule.getId())
                .map(old -> {
                    old.setBrand(vehicule.getBrand());
                    old.setFabricationDate(vehicule.getFabricationDate());
                    old.setFuelType(vehicule.getFuelType());
                    old.setGarage(vehicule.getGarage());
                    old.setAccessories(vehicule.getAccessories());

                    return vehiculeRepository.save(old);
                })
                .orElseThrow(() -> new IllegalArgumentException("Vehicule not found"));
    }

    public void delete(Long id) {
        Optional<Vehicule> found = vehiculeRepository.findById(id);

        if(found.isEmpty())
            throw  new IllegalArgumentException("Vehicule not found");

        vehiculeRepository.deleteById(id);
    }

    public List<Vehicule> finfByGarageId(Long garageId) {
        return vehiculeRepository.findByGarageId(garageId);
    }

    public Map<Garage, List<Vehicule>> findVehiculeByModelGroupByGarage(String brand){
        List<Vehicule> vehicules = vehiculeRepository.findByBrand(brand);

        if(CollectionUtils.isEmpty(vehicules))
            throw  new IllegalArgumentException(String.format("Vehicules with brand %s not found", brand));

        return vehicules.stream().collect(Collectors.groupingBy(Vehicule::getGarage));
    }
}
