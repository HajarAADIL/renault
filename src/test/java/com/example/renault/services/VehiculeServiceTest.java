package com.example.renault.services;

import com.example.renault.entities.GarageEntity;
import com.example.renault.entities.VehiculeEntity;
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
        GarageEntity garage1 = new GarageEntity();
        garage1.setId(1L);
        garage1.setName("Garage1");

        GarageEntity garage2 = new GarageEntity();
        garage2.setId(2L);
        garage2.setName("Garage2");

        VehiculeEntity v1 = new VehiculeEntity();
        v1.setBrand(brand);
        v1.setGarage(garage1);

        VehiculeEntity v2 = new VehiculeEntity();
        v2.setBrand(brand);
        v2.setGarage(garage1);

        VehiculeEntity v3 = new VehiculeEntity();
        v3.setBrand(brand);
        v3.setGarage(garage2);

        List<VehiculeEntity> vehicules = Arrays.asList(v1, v2, v3);

        when(vehiculeRepository.findByBrand(brand)).thenReturn(vehicules);

        Map<GarageEntity, List<VehiculeEntity>> result = vehiculeService.findVehiculeByModelGroupByGarage(brand);

        assertThat(result).hasSize(2);
        assertThat(result.get(garage1)).containsExactlyInAnyOrder(v1, v2);
        assertThat(result.get(garage2)).containsExactly(v3);
    }
}
