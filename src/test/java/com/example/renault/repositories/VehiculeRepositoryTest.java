package com.example.renault.repositories;

import com.example.renault.entities.*;
import com.example.renault.entities.VehiculeEntity;
import com.example.renault.enums.FuelTypeEnum;
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

        VehiculeEntity vehicule = new VehiculeEntity();
        vehicule.setBrand("Megane");
        vehicule.setFabricationDate(LocalDate.of(2025, 1, 1));
        vehicule.setFuelType(FuelTypeEnum.ELECTRIC);
        vehicule.setAccessories(List.of(new AccessoryEntity(), new AccessoryEntity()));

        vehicule = vehiculeRepository.save(vehicule);

        //Recherche
        Optional<VehiculeEntity> expected = vehiculeRepository.findById(vehicule.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getBrand()).isEqualTo("Megane");
        assertThat(expected.get().getFabricationDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(expected.get().getFuelType()).isEqualTo((FuelTypeEnum.ELECTRIC));
        assertThat(expected.get().getAccessories()).hasSize(2);

    }

    @Test
    void testFindVehiculeById(){

        VehiculeEntity vehicule = new VehiculeEntity();
        vehicule.setBrand("Megane");

        vehicule = vehiculeRepository.save(vehicule);

        //Recherche
        Optional<VehiculeEntity> expected = vehiculeRepository.findById(vehicule.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getBrand()).isEqualTo("Megane");


    }

    @Test
    void testDeleteVehicule(){

        VehiculeEntity vehicule = new VehiculeEntity();
        vehicule.setBrand("Megane");

        VehiculeEntity created = vehiculeRepository.save(vehicule);

        //Delete
        vehiculeRepository.delete(created);

        //Verif
        Optional<VehiculeEntity> expected = vehiculeRepository.findById(vehicule.getId());

        assertThat(expected.isEmpty());

    }
}
