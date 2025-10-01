package com.example.renault.services;

import com.example.renault.entities.Garage;
import com.example.renault.entities.Vehicule;
import com.example.renault.repositories.VehiculeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class VehiculeServiceTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @InjectMocks
    private VehiculeService vehiculeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindVehiculeByModelGroupByGarage() {

        String brand = "MEGANE";
        Garage garage1 = new Garage();
        garage1.setId(1L);
        garage1.setName("Garage1");

        Garage garage2 = new Garage();
        garage2.setId(2L);
        garage2.setName("Garage2");

        Vehicule v1 = new Vehicule();
        v1.setBrand(brand);
        v1.setGarage(garage1);

        Vehicule v2 = new Vehicule();
        v2.setBrand(brand);
        v2.setGarage(garage1);

        Vehicule v3 = new Vehicule();
        v3.setBrand(brand);
        v3.setGarage(garage2);

        List<Vehicule> vehicules = Arrays.asList(v1, v2, v3);

        when(vehiculeRepository.findByBrand(brand)).thenReturn(vehicules);

        Map<Garage, List<Vehicule>> result = vehiculeService.findVehiculeByModelGroupByGarage(brand);

        assertThat(result).hasSize(2);
        assertThat(result.get(garage1)).containsExactlyInAnyOrder(v1, v2);
        assertThat(result.get(garage2)).containsExactly(v3);
    }
}
