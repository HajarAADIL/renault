package com.example.renault.builders;

import com.example.renault.dto.GarageDTO;
import com.example.renault.entities.GarageEntity;

import java.util.ArrayList;

public class GarageBuilder {
    private Long id = null;
    private String name = "Bouskoura Garage";
    private String address = "123 Default Street";
    private String phone = "000000";
    private String email = "default@test.com";

    public static GarageBuilder aGarage() {
        return new GarageBuilder();
    }

    public GarageBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public GarageBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public GarageBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public GarageBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public GarageBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public GarageEntity build() {
        return GarageEntity.builder()
                .id(id)
                .name(name)
                .address(address)
                .phone(phone)
                .email(email)
                .vehicules(new ArrayList<>())
                .openingTimes(new ArrayList<>())
                .build();
    }
    public GarageDTO buildDto() {
        return new GarageDTO(
                id,
                name,
                address,
                phone,
                email,
                null,
                null
        );
    }
}
