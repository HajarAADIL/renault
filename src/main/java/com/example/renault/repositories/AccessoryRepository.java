package com.example.renault.repositories;

import com.example.renault.entities.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    List<Accessory> findByVehiculeId(Long id);
}
