package com.example.renault.controllers;

import com.example.renault.dto.VehiculeDTO;
import com.example.renault.entities.VehiculeEntity;
import com.example.renault.mapper.VehiculeMapper;
import com.example.renault.services.VehiculeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehiculeController.class)
public class VehiculeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private VehiculeMapper vehiculeMapper;

    @TestConfiguration
    static class TestConfig {

        @Bean
        VehiculeService vehiculeService() {
            return Mockito.mock(VehiculeService.class);
        }

        @Bean
        VehiculeMapper vehiculeMapper() {
            return Mockito.mock(VehiculeMapper.class);
        }
    }

    @Test
    void testSearchByGarage() throws Exception {
        Long garageId = 1L;

        VehiculeEntity vehicule = new VehiculeEntity();
        vehicule.setId(1L);
        vehicule.setBrand("CLIO");
        vehicule.setFabricationDate(LocalDate.of(2020, 1, 1));

        VehiculeDTO dto = new VehiculeDTO(
                vehicule.getId(),
                vehicule.getBrand(),
                vehicule.getFabricationDate(),
                vehicule.getFuelType(),
                garageId,
                null
        );

        when(vehiculeService.findByGarageId(garageId)).thenReturn(List.of(vehicule));
        when(vehiculeMapper.toDTO(vehicule)).thenReturn(dto);

        mockMvc.perform(get("/vehicules/garage/{id}", garageId)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("CLIO"))
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
