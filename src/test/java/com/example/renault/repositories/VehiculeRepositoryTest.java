package com.example.renault.repositories;

import com.example.renault.entities.*;
import com.example.renault.entities.Vehicule;
import com.example.renault.enums.FuelType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VehiculeRepositoryTest {
    
    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Test
    void testCreateVehicule(){

        Vehicule vehicule = new Vehicule();
        vehicule.setBrand("Toyota");
        vehicule.setFabricationDate(LocalDate.of(2025, 1, 1));
        vehicule.setFuelType(FuelType.ELECTRIC);
        vehicule.setAccessories(List.of(new Accessory(), new Accessory()));

        vehicule = vehiculeRepository.save(vehicule);

        //Recherche
        Optional<Vehicule> expected = vehiculeRepository.findById(vehicule.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getBrand()).isEqualTo("Toyota");
        assertThat(expected.get().getFabricationDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(expected.get().getFuelType()).isEqualTo((FuelType.ELECTRIC));
        assertThat(expected.get().getAccessories()).hasSize(2);

    }

    @Test
    void testFindVehiculeById(){

        Vehicule vehicule = new Vehicule();
        vehicule.setBrand("Toyota");

        vehicule = vehiculeRepository.save(vehicule);

        //Recherche
        Optional<Vehicule> expected = vehiculeRepository.findById(vehicule.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getBrand()).isEqualTo("Toyota");


    }

    @Test
    void testDeleteVehicule(){

        Vehicule vehicule = new Vehicule();
        vehicule.setBrand("Toyota");

        Vehicule created = vehiculeRepository.save(vehicule);

        //Delete
        vehiculeRepository.delete(created);

        //Verif
        Optional<Vehicule> expected = vehiculeRepository.findById(vehicule.getId());

        assertThat(expected.isEmpty());

    }
}
