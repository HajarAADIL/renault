package com.example.renault.repositories;

import com.example.renault.entities.VehiculeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<VehiculeEntity, Long> {

    List<VehiculeEntity> findByGarageId(Long id);

    List<VehiculeEntity> findByBrand(String brand);

    long countByGarageId(Long id);
}
