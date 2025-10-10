package com.example.renault.integration;

import com.example.renault.builders.AccessoryBuilder;
import com.example.renault.builders.GarageBuilder;
import com.example.renault.builders.VehiculeBuilder;
import com.example.renault.dto.AccessoryDTO;
import com.example.renault.entities.AccessoryEntity;
import com.example.renault.repositories.AccessoryRepository;
import com.example.renault.repositories.GarageRepository;
import com.example.renault.repositories.VehiculeRepository;
import com.example.renault.services.AccessoryService;
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

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class AccessoryServiceIT {

    @LocalServerPort
    int port;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private AccessoryService accessoryService;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        accessoryRepository.deleteAll();
        vehiculeRepository.deleteAll();
        garageRepository.deleteAll();
    }

    @Test
    void shouldCreateAccessorySuccessfully() {
        var garage = garageRepository.save(GarageBuilder.aGarage().build());
        var vehicule = vehiculeRepository.save(
                VehiculeBuilder.aVehicule().withGarage(garage).build()
        );
        var accessoryDto = AccessoryBuilder.anAccessory()
                .withVehicule(vehicule)
                .buildDto();

        var created = given()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(accessoryDto)
                    .when()
                        .post("/accessories")
                    .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(AccessoryDTO.class);

        assertThat(created)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(accessoryDto);

        var saved = accessoryRepository.findById(created.id()).get();

        assertThat(saved)
                .extracting(AccessoryEntity::getName)
                .isEqualTo(accessoryDto.name());
    }

    @Test
    void shouldUpdateAccessorySuccessfully() {
        var garage = garageRepository.save(GarageBuilder.aGarage().build());
        var vehicule = vehiculeRepository.save(VehiculeBuilder.aVehicule().withGarage(garage).build());
        var existing = accessoryRepository.save(AccessoryBuilder.anAccessory().withVehicule(vehicule).build());

        var updatedDto = AccessoryBuilder.anAccessory()
                .withVehicule(vehicule)
                .withName("New Accessory")
                .buildDto();

        var updated = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatedDto)
            .when()
                .put("/accessories/{id}", existing.getId())
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AccessoryDTO.class);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(updatedDto);

        var saved = accessoryRepository.findById(updated.id()).get();

        assertThat(saved)
                .extracting(AccessoryEntity::getName)
                .isEqualTo("New Accessory");

    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingAccessory() {
        given()
            .when()
                .delete("/accessories/{id}", 99L)
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("error", containsString("Accessory not found"));
    }
}
