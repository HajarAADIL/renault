package com.example.renault.services;

import com.example.renault.entities.Accessory;
import com.example.renault.repositories.AccessoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public Accessory create(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    public Accessory update(Accessory accessory) {
        return accessoryRepository.findById(accessory.getId())
                .map(old -> {
                    old.setType(accessory.getType());
                    old.setPrice(accessory.getPrice());
                    old.setName(accessory.getName());
                    old.setDescription(accessory.getDescription());
                    old.setVehicule(accessory.getVehicule());

                    return accessoryRepository.save(old);
                })
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));
    }

    public void delete(Long id) {
        Optional<Accessory> found = accessoryRepository.findById(id);

        if(found.isEmpty())
            throw  new IllegalArgumentException("Accessory not found");

        accessoryRepository.deleteById(id);
    }

    public List<Accessory> finfByGarageId(Long garageId) {
        return accessoryRepository.findByVehiculeId(garageId);
    }
}
