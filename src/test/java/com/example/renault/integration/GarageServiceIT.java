package com.example.renault.integration;


import com.example.renault.entities.GarageEntity;
import com.example.renault.repositories.GarageRepository;
import com.example.renault.services.GarageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GarageServiceIT {

    @Autowired
    private GarageService garageService;

    @Autowired
    private GarageRepository garageRepository;

    @Test
    void testAddGarage() {
        GarageEntity garage = new GarageEntity();
        garage.setName("Bouskoura Garage");
        GarageEntity saved = garageService.create(garage);

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("Bouskoura Garage", saved.getName());
    }

    @Test
    void testGetGarageById() {
        GarageEntity garage = new GarageEntity();
        garage.setName("Bouskoura Garage");
        GarageEntity saved = garageRepository.save(garage);

        Optional<GarageEntity> result = garageService.findById(saved.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Bouskoura Garage", result.get().getName());
    }
}
