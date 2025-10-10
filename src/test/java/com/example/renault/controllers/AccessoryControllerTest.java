package com.example.renault.controllers;

import com.example.renault.exception.GlobalExceptionHandler;
import com.example.renault.services.AccessoryService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@WebMvcTest(AccessoryController.class)
public class AccessoryControllerTest {

    @Autowired
    private AccessoryService accessoryService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        AccessoryService accessoryService() {
            return Mockito.mock(AccessoryService.class);
        }
    }

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new AccessoryController(accessoryService));

        var controller = new AccessoryController(accessoryService);
        var advice = new GlobalExceptionHandler();

        var mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(advice)
                .build();

        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldDeleteAccessorySuccessfully(){
        var accessoryId = 1L;

        RestAssuredMockMvc.given()
                .when()
                    .delete("/accessories/{id}", accessoryId)
                .then()
                    .status(HttpStatus.NO_CONTENT);

        verify(accessoryService, times(1)).delete(accessoryId);
    }

    @Test
    void shouldReturnNotFoundWhenAccessoryDoesntExist(){
        var nonExistentId = 99L;

        doThrow(new EntityNotFoundException("Accessory not found with id " + nonExistentId))
                .when(accessoryService)
                .delete(nonExistentId);

        RestAssuredMockMvc.given()
                .when()
                    .delete("/accessories/{id}", nonExistentId)
                .then()
                    .status(HttpStatus.NOT_FOUND);

        verify(accessoryService, times(1)).delete(nonExistentId);
    }


}
