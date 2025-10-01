package com.example.renault.repositories;

import com.example.renault.entities.Garage;
import com.example.renault.enums.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    @Query("SELECT DISTINCT g FROM Garage g " +
            "JOIN g.vehicules v " +
            "JOIN v.accessories a " +
            "WHERE a.name = :accessoryName")
    List<Garage> findByAccessoryId(@Param("accessoryName") String accessoryName);

    @Query("SELECT DISTINCT g FROM Garage g " +
            "JOIN g.vehicules v " +
            "WHERE v.fuelType = :fuel")
    List<Garage> findByFuelType(@Param("fuel") FuelType fuelType);
}
