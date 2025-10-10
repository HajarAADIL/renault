package com.example.renault.dto;

import com.example.renault.enums.AccessoryTypeEnum;

import java.math.BigDecimal;

public record AccessoryDTO(Long id,
                           String name,
                           String description,
                           BigDecimal price,
                           AccessoryTypeEnum type,
                           Long vehiculeId) {
}
