package com.example.renault.services;

import com.example.renault.entities.Accessory;
import com.example.renault.repositories.AccessoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public Accessory create(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    public Optional<Accessory> update(Long id, Accessory accessory) {
        return accessoryRepository.findById(id)
                .map(old -> {
                    old.setType(accessory.getType());
                    old.setPrice(accessory.getPrice());
                    old.setName(accessory.getName());
                    old.setDescription(accessory.getDescription());
                    old.setVehicule(accessory.getVehicule());

                    return accessoryRepository.save(old);
                });
    }

    public boolean delete(Long id) {
        if (accessoryRepository.existsById(id)) {
            accessoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Accessory> findByVehiculeId(Long garageId) {
        return accessoryRepository.findByVehiculeId(garageId);
    }
}
