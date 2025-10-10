package com.example.renault.services;

import com.example.renault.builders.GarageBuilder;
import com.example.renault.entities.GarageEntity;
import com.example.renault.repositories.GarageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GarageServiceTest {

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private GarageService garageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateGarageSuccessfully() {

        var garageId = 1L;
        var existingGarage = GarageBuilder.aGarage().build();

        GarageEntity updatedGarage = GarageBuilder.aGarage()
                .withName("Oasis Garage")
                .withAddress("New Address")
                .build();

        when(garageRepository.findById(garageId)).thenReturn(Optional.of(existingGarage));
        when(garageRepository.save(any(GarageEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var result = garageService.update(garageId, updatedGarage);

        assertThat(result).isPresent();

        var garage = result.get();
        assertThat(garage.getName()).isEqualTo("Oasis Garage");
        assertThat(garage.getAddress()).isEqualTo("New Address");
        verify(garageRepository, times(1)).save(any(GarageEntity.class));
    }
}
