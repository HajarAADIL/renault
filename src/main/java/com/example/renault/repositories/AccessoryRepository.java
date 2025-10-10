package com.example.renault.repositories;

import com.example.renault.entities.AccessoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<AccessoryEntity, Long> {
    List<AccessoryEntity> findByVehiculeId(Long id);
}
