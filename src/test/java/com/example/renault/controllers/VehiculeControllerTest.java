package com.example.renault.controllers;

import com.example.renault.builders.GarageBuilder;
import com.example.renault.builders.VehiculeBuilder;
import com.example.renault.enums.FuelTypeEnum;
import com.example.renault.mapper.VehiculeMapper;
import com.example.renault.services.VehiculeService;
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

@WebMvcTest(VehiculeController.class)
public class VehiculeControllerTest {

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

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new VehiculeController(vehiculeService, vehiculeMapper));
    }

    @Test
    void shouldUpdateVehiculeWhenChangeBrandGarage() throws Exception {

        var garage = GarageBuilder.aGarage().withId(10L).withName("Ville Verte Garage").build();
        var vehiculeId = 1L;

        var updatedVehiculeDTO = VehiculeBuilder.aVehicule().withId(vehiculeId).withBrand("Clio 5").withGarage(garage).buildDto();

        when(vehiculeService.update(vehiculeId, updatedVehiculeDTO)).thenReturn(Optional.of(updatedVehiculeDTO));

        RestAssuredMockMvc.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(updatedVehiculeDTO)
                .when()
                    .put("/vehicules/{id}", vehiculeId)
                .then()
                    .status(HttpStatus.OK)
                    .body("brand",equalTo("Clio 5"))
                    .body("fuelType", equalTo(FuelTypeEnum.DIESEL.name())) //unchanged
                    .body("garageId", equalTo(10));
    }

    @Test
    void shouldReturnNotFoundWhenVehiculeDontExist(){
        var nonExistentId = 99L;

        var updateVehicule = VehiculeBuilder.aVehicule().withId(nonExistentId).build();

        var updateVehiculeDTO = VehiculeBuilder.aVehicule().buildDto();

        when(vehiculeService.update(nonExistentId, updateVehiculeDTO)).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(updateVehiculeDTO)
                .when()
                    .put("/vehicules/{id}", nonExistentId)
                .then()
                    .status(HttpStatus.NOT_FOUND);
    }
}
