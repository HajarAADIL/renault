package com.example.renault.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Openings> openingTimes = new ArrayList<>();

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL)
    private List<Vehicule> vehicules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Openings> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<Openings> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public void addOpening(Openings openings) {
        openings.setGarage(this);
        this.openingTimes.add(openings);
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }
}
