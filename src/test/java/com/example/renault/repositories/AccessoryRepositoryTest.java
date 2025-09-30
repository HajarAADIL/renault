package com.example.renault.repositories;

import com.example.renault.entities.Accessory;
import com.example.renault.entities.Accessory;
import com.example.renault.enums.AccessoryType;
import com.example.renault.enums.FuelType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccessoryRepositoryTest {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Test
    void testCreateAccessory(){

        Accessory accessory = new Accessory();
        accessory.setName("Caméras de recul");
        accessory.setDescription("Améliorent la visibilité");
        accessory.setPrice(BigDecimal.valueOf(350000));
        accessory.setType(AccessoryType.CONFORT);

        accessory = accessoryRepository.save(accessory);

        //Recherche
        Optional<Accessory> expected = accessoryRepository.findById(accessory.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getName()).isEqualTo("Caméras de recul");
        assertThat(expected.get().getDescription()).isEqualTo("Améliorent la visibilité");
        assertThat(expected.get().getPrice()).isEqualTo(BigDecimal.valueOf(350000));
        assertThat(expected.get().getType()).isEqualTo(AccessoryType.CONFORT);

    }

    @Test
    void testFindAccessoryById(){

        Accessory accessory = new Accessory();
        accessory.setType(AccessoryType.EMERGENCY);

        accessory = accessoryRepository.save(accessory);

        //Recherche
        Optional<Accessory> expected = accessoryRepository.findById(accessory.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getType()).isEqualTo(AccessoryType.EMERGENCY);


    }

    @Test
    void testDeleteAccessory(){

        Accessory accessory = new Accessory();

        Accessory created = accessoryRepository.save(accessory);

        //Delete
        accessoryRepository.delete(created);

        //Verif
        Optional<Accessory> expected = accessoryRepository.findById(accessory.getId());

        assertThat(expected.isEmpty());

    }
}
