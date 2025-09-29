package com.example.renault.entities;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Entity
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL)
    private List<Openings> horaires;

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

    public List<Openings> getHoraires() {
        return horaires;
    }

    public void setHoraires(List<Openings> horaires) {
        this.horaires = horaires;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }
}
