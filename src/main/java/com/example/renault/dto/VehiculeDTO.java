package com.example.renault.dto;

import com.example.renault.enums.FuelTypeEnum;

import java.time.LocalDate;
import java.util.List;

public record VehiculeDTO(Long id,
                          String brand,
                          LocalDate fabricationDate,
                          FuelTypeEnum fuelType,
                          Long garageId,
                          List<AccessoryDTO> accessories) {
}
