package com.example.renault.controllers;

import com.example.renault.builders.GarageBuilder;
import com.example.renault.mapper.GarageMapper;
import com.example.renault.services.GarageService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(GarageController.class)
public class GarageControllerTest {

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

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new GarageController(garageService, garageMapper));
    }

    @Test
    void shouldReturnGarageWhenFoundById() {

        var garageId = 1L;
        var garageEntity = GarageBuilder.aGarage().build();

        var garageDTO = GarageBuilder.aGarage().buildDto();

        when(garageService.findById(garageId)).thenReturn(Optional.of(garageEntity));
        when(garageMapper.toDTO(garageEntity)).thenReturn(garageDTO);

        RestAssuredMockMvc.given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/garages/{id}", garageId)
                .then()
                    .status(HttpStatus.OK)
                    .body("name", equalTo("Bouskoura Garage"));

    }

    @Test
    void shouldReturnNotFoundWhenGarageDontExist() {
        var nonExistentId = 99L;
        when(garageService.findById(nonExistentId)).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/garages/{id}", nonExistentId)
                .then()
                    .status(HttpStatus.NOT_FOUND);

    }
}
