package com.example.renault.repositories;

import com.example.renault.entities.GarageEntity;
import com.example.renault.enums.FuelTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GarageRepository extends JpaRepository<GarageEntity, Long> {

    @Query("SELECT DISTINCT g FROM GarageEntity g " +
            "JOIN g.vehicules v " +
            "JOIN v.accessories a " +
            "WHERE a.name = :accessoryName")
    List<GarageEntity> findByAccessoryId(@Param("accessoryName") String accessoryName);

    @Query("SELECT DISTINCT g FROM GarageEntity g " +
            "JOIN g.vehicules v " +
            "WHERE v.fuelType = :fuel")
    List<GarageEntity> findByFuelType(@Param("fuel") FuelTypeEnum fuelType);
}
