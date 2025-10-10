package com.example.renault.services;

import com.example.renault.builders.AccessoryBuilder;
import com.example.renault.repositories.AccessoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccessoryServiceTest {

    @Mock
    private AccessoryRepository accessoryRepository;

    @InjectMocks
    private AccessoryService accessoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteAccessorySuccessfully() {

        var id = 1L;
        var accessory = AccessoryBuilder.anAccessory().build();

        when(accessoryRepository.findById(id)).thenReturn(Optional.of(accessory));

        accessoryService.delete(id);

        //assertThat(result).isTrue();
        verify(accessoryRepository).findById(id);
        verify(accessoryRepository).delete(accessory);
    }
}
