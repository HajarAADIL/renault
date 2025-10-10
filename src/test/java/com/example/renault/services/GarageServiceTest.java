package com.example.renault.services;

import com.example.renault.entities.GarageEntity;
import com.example.renault.repositories.GarageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
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
    void testUpdateGarage() {

        Long garageId = 1L;
        GarageEntity oldGarage = new GarageEntity();
        oldGarage.setId(garageId);
        oldGarage.setName("OldName");
        oldGarage.setOpeningTimes(Collections.emptyList());

        GarageEntity newGarage = new GarageEntity();
        newGarage.setName("NewName");
        newGarage.setAddress("NewAddress");
        newGarage.setEmail("renaul@contact.com");
        newGarage.setVehicules(Collections.emptyList());
        newGarage.setOpeningTimes(Collections.emptyList());

        when(garageRepository.findById(garageId)).thenReturn(Optional.of(oldGarage));
        when(garageRepository.save(any(GarageEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<GarageEntity> result = garageService.update(garageId, newGarage);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("NewName");
        assertThat(result.get().getAddress()).isEqualTo("NewAddress");
        assertThat(result.get().getEmail()).isEqualTo("renaul@contact.com");
        verify(garageRepository, times(1)).save(any(GarageEntity.class));
    }
}
