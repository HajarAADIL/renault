package com.example.renault.services;

import com.example.renault.entities.Garage;
import com.example.renault.entities.Vehicule;
import com.example.renault.repositories.VehiculeRepository;
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
    
    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public Vehicule create(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    public Optional<Vehicule> update(Long id, Vehicule vehicule) {
        return vehiculeRepository.findById(id)
                .map(old -> {
                    old.setBrand(vehicule.getBrand());
                    old.setFabricationDate(vehicule.getFabricationDate());
                    old.setFuelType(vehicule.getFuelType());
                    old.setGarage(vehicule.getGarage());
                    old.setAccessories(vehicule.getAccessories());

                    return vehiculeRepository.save(old);
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
