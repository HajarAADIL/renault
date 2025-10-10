package com.example.renault.builders;

import com.example.renault.dto.VehiculeDTO;
import com.example.renault.entities.GarageEntity;
import com.example.renault.entities.VehiculeEntity;
import com.example.renault.enums.FuelTypeEnum;

import java.time.LocalDate;
import java.util.ArrayList;

public class VehiculeBuilder {
    private Long id = null;
    private String brand = "Megane";
    private LocalDate fabricationDate = LocalDate.of(2020, 1, 1);
    private FuelTypeEnum fuelType = FuelTypeEnum.DIESEL;
    private GarageEntity garage = GarageBuilder.aGarage().build();

    public static VehiculeBuilder aVehicule() {
        return new VehiculeBuilder();
    }
    public VehiculeBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public VehiculeBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public VehiculeBuilder withFabricationDate(LocalDate date) {
        this.fabricationDate = date;
        return this;
    }

    public VehiculeBuilder withFuelType(FuelTypeEnum fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    public VehiculeBuilder withGarage(GarageEntity garage) {
        this.garage = garage;
        return this;
    }

    public VehiculeEntity build() {
        return VehiculeEntity.builder()
                .id(id)
                .brand(brand)
                .fabricationDate(fabricationDate)
                .fuelType(fuelType)
                .garage(garage)
                .build();
    }

    public VehiculeDTO buildDto() {
        return new VehiculeDTO(
                id,
                brand,
                fabricationDate,
                fuelType,
                garage.getId(),
                new ArrayList<>()
        );
    }


}
