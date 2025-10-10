package com.example.renault.integration;

import com.example.renault.builders.GarageBuilder;
import com.example.renault.builders.VehiculeBuilder;
import com.example.renault.dto.VehiculeDTO;
import com.example.renault.entities.VehiculeEntity;
import com.example.renault.enums.FuelTypeEnum;
import com.example.renault.repositories.GarageRepository;
import com.example.renault.repositories.VehiculeRepository;
import com.example.renault.services.VehiculeService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class VehiculeServiceIT {

    @LocalServerPort
    int port;

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private GarageRepository garageRepository;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        garageRepository.deleteAll();
        vehiculeRepository.deleteAll();
    }


    @Test
    void shouldCreateVehiculeSuccessfully() {
        var garage = garageRepository.save(GarageBuilder.aGarage().build());
        var vehiculeDTO = VehiculeBuilder.aVehicule().withGarage(garage).buildDto();

        var created = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(vehiculeDTO)
                .when()
                    .post("/vehicules")
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(VehiculeDTO.class);

        assertThat(created)
                .usingRecursiveComparison()
                .ignoringFields("id","accessories")
                .isEqualTo(vehiculeDTO);

        var saved = vehiculeRepository.findById(created.id()).get();

        assertThat(saved)
                .extracting(VehiculeEntity::getBrand)
                .isEqualTo(vehiculeDTO.brand());
    }

    @Test
    void shouldFailToCreateVehiculeWhenGarageNotFound() {

        var vehiculeDTO = VehiculeBuilder.aVehicule()
                .withGarage(GarageBuilder.aGarage().withId(99L).build())
                .buildDto();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(vehiculeDTO)
                .when()
                    .post("/vehicules")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                .body("error", equalTo("Garage not found"));
    }

    @Test
    void shouldFailToCreateVehiculeWhenGarageIsFull() {
        var garage = garageRepository.save(GarageBuilder.aGarage().build());

        for (int i = 0; i < VehiculeService.MAX_VEHICULE; i++) {
            vehiculeRepository.save(VehiculeBuilder.aVehicule().withGarage(garage).build());
        }

        var dto = VehiculeBuilder.aVehicule().withGarage(garage).buildDto();

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(dto)
        .when()
            .post("/vehicules")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("error", containsString("maximum number of vehicles"));
    }

    @Test
    void shouldUpdateVehiculeSuccessfully() {
        var garage = garageRepository.save(GarageBuilder.aGarage().build());
        var vehicule = vehiculeRepository.save(VehiculeBuilder.aVehicule().withGarage(garage).build());

        var vehiculeDto = VehiculeBuilder.aVehicule()
                .withId(vehicule.getId())
                .withFuelType(FuelTypeEnum.ELECTRIC)
                .withGarage(garage).buildDto();

        var updatedDto = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(vehiculeDto)
                .when()
                    .put("/vehicules/{id}", vehicule.getId())
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(VehiculeDTO.class);

        assertThat(updatedDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(vehiculeDto);

        var updatedEntity = vehiculeRepository.findById(vehicule.getId()).get();

        assertThat(updatedEntity)
                .extracting(VehiculeEntity::getFuelType)
                .isEqualTo(FuelTypeEnum.ELECTRIC);
    }
}
