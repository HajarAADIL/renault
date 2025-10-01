package com.example.renault.dto;

import com.example.renault.enums.AccessoryType;

import java.math.BigDecimal;

public record AccessoryDTO(Long id,
                           String name,
                           String description,
                           BigDecimal price,
                           AccessoryType type,
                           Long vehiculeId) {
}
