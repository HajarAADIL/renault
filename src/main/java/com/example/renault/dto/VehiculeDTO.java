package com.example.renault.dto;

import com.example.renault.enums.FuelType;

import java.time.LocalDate;
import java.util.List;

public record VehiculeDTO(Long id,
                          String brand,
                          LocalDate fabricationDate,
                          FuelType fuelType,
                          Long garageId,
                          List<AccessoryDTO> accessories) {
}
