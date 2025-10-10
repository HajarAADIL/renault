package com.example.renault.services;

import com.example.renault.builders.GarageBuilder;
import com.example.renault.builders.VehiculeBuilder;
import com.example.renault.repositories.VehiculeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

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

        var brand = "MEGANE";
        var garage1 = GarageBuilder.aGarage().withId(1L).withName("Garage1").build();
        var garage2 = GarageBuilder.aGarage().withId(2L).withName("Garage2").build();

        var v1 = VehiculeBuilder.aVehicule().withBrand(brand).withGarage(garage1).build();
        var v2 = VehiculeBuilder.aVehicule().withBrand(brand).withGarage(garage1).build();
        var v3 = VehiculeBuilder.aVehicule().withBrand(brand).withGarage(garage2).build();

        var vehicules = List.of(v1, v2, v3);

        when(vehiculeRepository.findByBrand(brand)).thenReturn(vehicules);

        var result = vehiculeService.findVehiculeByModelGroupByGarage(brand);

        assertThat(result).hasSize(2);
        assertThat(result.get(garage1)).containsExactlyInAnyOrder(v1, v2);
        assertThat(result.get(garage2)).containsExactly(v3);
    }
}
