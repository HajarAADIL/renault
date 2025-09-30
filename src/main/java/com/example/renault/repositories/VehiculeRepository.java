package com.example.renault.repositories;

import com.example.renault.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByGarageId(Long id);

    List<Vehicule> findByBrand(String brand);
}
