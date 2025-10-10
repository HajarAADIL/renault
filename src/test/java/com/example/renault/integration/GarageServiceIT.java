package com.example.renault.integration;


import com.example.renault.builders.GarageBuilder;
import com.example.renault.dto.GarageDTO;
import com.example.renault.entities.GarageEntity;
import com.example.renault.repositories.GarageRepository;
import com.example.renault.services.GarageService;
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

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class GarageServiceIT {

    @LocalServerPort
    int port;

    @Autowired
    private GarageService garageService;

    @Autowired
    private GarageRepository garageRepository;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        garageRepository.deleteAll();
    }

    @Test
    void shouldCreateGarageSuccessfully() {
        var garageDTO = GarageBuilder.aGarage().buildDto();

        var saved = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(garageDTO)
                .when()
                        .post("/garages")
                .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(GarageDTO.class);

        assertThat(saved)
                .usingRecursiveComparison()
                .ignoringFields("id") // because it’s generated
                .isEqualTo(garageDTO);
    }

    @Test
    void shouldUpdateGarageSuccessfully() {

        var existing = garageRepository.save(GarageBuilder.aGarage().build());

        var newGarage = GarageBuilder.aGarage()
                .withName("Oasis Garage")
                .withAddress("New Adress")
                .withEmail("new@renault.com")
                .withPhone("1111111")
                .buildDto();

        var updatedGarage = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(newGarage)
                .when()
                    .put("/garages/{id}", existing.getId())
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(GarageDTO.class);

        var found = garageRepository.findById(existing.getId()).orElseThrow();

        assertThat(updatedGarage)
                .usingRecursiveComparison()
                .ignoringFields("id", "openingTimes", "vehicules")
                .isEqualTo(newGarage);

        assertThat(found)
                .extracting(GarageEntity::getName)
                .isEqualTo("Oasis Garage");
    }

    @Test
    void shouldReturnEmptyWhenUpdatingNonExistingGarage() {

        Long missingId = 404L;

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(GarageBuilder.aGarage().buildDto())
        .when()
            .put("/garages/{id}", missingId)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldFindGarageWhenGetById() {

        var saved = garageRepository.save(GarageBuilder.aGarage().build());

        var found = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/garages/{id}", saved.getId())
                .then()
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(GarageDTO.class);

        assertThat(found)
                .usingRecursiveComparison()
                .ignoringFields("vehicules", "openingTimes")
                .isEqualTo(GarageBuilder.aGarage().withId(saved.getId()).buildDto());
    }

    @Test
    void shouldDeleteGarageSuccessfully() {

        var saved = garageRepository.save(GarageBuilder.aGarage().build());

        given()
            .when()
                .delete("/garages/{id}", saved.getId())
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertThat(garageRepository.existsById(saved.getId())).isFalse();
    }

}
