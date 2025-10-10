package com.example.renault.builders;

import com.example.renault.dto.AccessoryDTO;
import com.example.renault.entities.AccessoryEntity;
import com.example.renault.entities.VehiculeEntity;
import com.example.renault.enums.AccessoryTypeEnum;

import java.math.BigDecimal;

public class AccessoryBuilder {
    private Long id = null;
    private String name = "Caméras de recul";
    private String description = "Améliorent la visibilité";
    private BigDecimal price = BigDecimal.valueOf(5000);
    private AccessoryTypeEnum type = AccessoryTypeEnum.CONFORT;
    private VehiculeEntity vehicule = VehiculeBuilder.aVehicule().build();

    public static AccessoryBuilder anAccessory() {
        return new AccessoryBuilder();
    }
    public AccessoryBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AccessoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AccessoryBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public AccessoryBuilder withType(AccessoryTypeEnum type) {
        this.type = type;
        return this;
    }

    public AccessoryBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public AccessoryBuilder withVehicule(VehiculeEntity vehicule) {
        this.vehicule = vehicule;
        return this;
    }

    public AccessoryEntity build() {
        return AccessoryEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .type(type)
                .vehicule(vehicule)
                .build();
    }

    public AccessoryDTO buildDto() {
        return new AccessoryDTO(
                id,
                name,
                description,
                price,
                type,
                vehicule.getId()
        );
    }
}
