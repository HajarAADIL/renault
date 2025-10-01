package com.example.renault.services;

import com.example.renault.dto.AccessoryDTO;
import com.example.renault.entities.Accessory;
import com.example.renault.entities.Vehicule;
import com.example.renault.mapper.AccessoryMapper;
import com.example.renault.repositories.AccessoryRepository;
import com.example.renault.repositories.VehiculeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final VehiculeRepository vehiculeRepository;
    private final AccessoryMapper accessoryMapper;

    public AccessoryService(AccessoryRepository accessoryRepository,
                            VehiculeRepository vehiculeRepository,
                            AccessoryMapper accessoryMapper) {
        this.accessoryRepository = accessoryRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.accessoryMapper = accessoryMapper;
    }

    public AccessoryDTO create(AccessoryDTO accessoryDto) {
        Vehicule vehicule = vehiculeRepository.findById(accessoryDto.vehiculeId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found"));

        Accessory accessory = accessoryMapper.toEntity(accessoryDto);
        accessory.setVehicule(vehicule);
        return accessoryMapper.toDTO(accessoryRepository.save(accessory));
    }

    public Optional<AccessoryDTO> update(Long id, AccessoryDTO accessoryDto) {
        Vehicule vehicule = vehiculeRepository.findById(accessoryDto.vehiculeId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found"));

        return accessoryRepository.findById(id)
                .map(old -> {
                    old.setType(accessoryDto.type());
                    old.setPrice(accessoryDto.price());
                    old.setName(accessoryDto.name());
                    old.setDescription(accessoryDto.description());
                    old.setVehicule(vehicule);

                    return accessoryMapper.toDTO(accessoryRepository.save(old));
                });
    }

    public boolean delete(Long id) {
        if (accessoryRepository.existsById(id)) {
            accessoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<AccessoryDTO> findByVehiculeId(Long garageId) {
        return accessoryMapper.toDTO(accessoryRepository.findByVehiculeId(garageId));
    }
}
