package com.example.renault.entities;

import com.example.renault.enums.FuelTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private LocalDate fabricationDate;

    @Enumerated(EnumType.STRING)
    private FuelTypeEnum fuelType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private GarageEntity garage;

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    private List<AccessoryEntity> accessories;

}
