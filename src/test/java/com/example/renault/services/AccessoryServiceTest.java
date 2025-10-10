package com.example.renault.services;

import com.example.renault.repositories.AccessoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testDeleteAccessory() {

        Long id = 1L;
        when(accessoryRepository.existsById(id)).thenReturn(true);

        //boolean result = accessoryService.delete(id);

        //assertThat(result).isTrue();
        verify(accessoryRepository, times(1)).deleteById(id);
    }
}
