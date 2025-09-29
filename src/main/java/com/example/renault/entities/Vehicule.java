package com.example.renault.entities;

import com.example.renault.enums.FuelType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private LocalDate fabricationDate;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    private List<Accessory> accessories;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDate getFabricationDate() {
        return fabricationDate;
    }

    public void setFabricationDate(LocalDate fabricationDate) {
        this.fabricationDate = fabricationDate;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }
}
