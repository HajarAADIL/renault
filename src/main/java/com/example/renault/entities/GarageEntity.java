package com.example.renault.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GarageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<OpeningsEntity> openingTimes = new ArrayList<>();

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL)
    private List<VehiculeEntity> vehicules;

    public void addOpening(OpeningsEntity openings) {
        openings.setGarage(this);
        this.openingTimes.add(openings);
    }
}
