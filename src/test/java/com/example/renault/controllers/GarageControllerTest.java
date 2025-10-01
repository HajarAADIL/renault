package com.example.renault.controllers;

import com.example.renault.dto.GarageDTO;
import com.example.renault.entities.Garage;
import com.example.renault.mapper.GarageMapper;
import com.example.renault.services.GarageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GarageController.class)
public class GarageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GarageService garageService;

    @Autowired
    private GarageMapper garageMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        GarageService garageService() {
            return Mockito.mock(GarageService.class);
        }

        @Bean
        GarageMapper garageMapper() {
            return Mockito.mock(GarageMapper.class);
        }
    }

    @Test
    void testSearchById() throws Exception {

        Long garageId = 1L;
        Garage garage = new Garage();
        garage.setId(garageId);
        garage.setName("Bouskoura Garage");

        GarageDTO dto = new GarageDTO(garageId, "Bouskoura Garage", "Address", "123456","email@test.com", null , null);

        when(garageService.findById(garageId)).thenReturn(Optional.of(garage));
        when(garageMapper.toDTO(garage)).thenReturn(dto);

        // when & then
        mockMvc.perform(get("/garages/{id}", garageId)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bouskoura Garage"));
    }
}
