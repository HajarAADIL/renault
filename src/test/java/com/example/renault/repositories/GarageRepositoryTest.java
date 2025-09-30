package com.example.renault.repositories;

import com.example.renault.entities.Garage;
import com.example.renault.entities.OpeningTime;
import com.example.renault.entities.Openings;
import com.example.renault.entities.Vehicule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GarageRepositoryTest {

    @Autowired
    private GarageRepository garageRepository;

    @Test
    void testCreateGarage(){

        Garage garage = new Garage();
        garage.setName("Garage Bouskoura");
        garage.setAddress("Bouskoura");
        garage.setEmail("bouskoura@renault.com");
        garage.setPhone("+212 666555555");

        //Openings
        OpeningTime ot = new OpeningTime();
        ot.setStartTime(LocalTime.of(9,0));
        ot.setEndTime(LocalTime.MIDNIGHT);

        Openings o = new Openings();
        o.setDayOfWeek(DayOfWeek.MONDAY);
        o.setOpeningTimes(List.of(ot));

        garage.setHoraires(List.of(o));

        //Vehicule
        garage.setVehicules(List.of(new Vehicule()));

        garage = garageRepository.save(garage);

        //Recherche
        Optional<Garage> expected = garageRepository.findById(garage.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getName()).isEqualTo("Garage Bouskoura");
        assertThat(expected.get().getAddress()).isEqualTo("Bouskoura");
        assertThat(expected.get().getEmail()).isEqualTo("bouskoura@renault.com");
        assertThat(expected.get().getPhone()).isEqualTo("+212 666555555");
        assertThat(expected.get().getHoraires()).hasSize(1);
        assertThat(expected.get().getHoraires().get(0).getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(expected.get()
                .getHoraires()
                .get(0)
                .getOpeningTimes()).hasSize(1);

        assertThat(expected.get()
                .getHoraires()
                .get(0)
                .getOpeningTimes()
                .get(0).getStartTime()).isEqualTo(LocalTime.of(9,0));

        assertThat(expected.get()
                .getHoraires()
                .get(0)
                .getOpeningTimes()
                .get(0).getEndTime()).isEqualTo(LocalTime.MIDNIGHT);


        assertThat(expected.get()
                .getVehicules()).hasSize(1);

    }

    @Test
    void testFindGarageById(){

        Garage garage = new Garage();
        garage.setName("Garage Bouskoura");

        garage = garageRepository.save(garage);

        //Recherche
        Optional<Garage> expected = garageRepository.findById(garage.getId());

        //Verif
        assertThat(expected.isPresent());
        assertThat(expected.get().getName()).isEqualTo("Garage Bouskoura");


    }

    @Test
    void testDeleteGarage(){

        Garage garage = new Garage();
        garage.setName("Garage Bouskoura");

        Garage created = garageRepository.save(garage);

        //Delete
        garageRepository.delete(created);
        
        //Verif
        Optional<Garage> expected = garageRepository.findById(garage.getId());

        assertThat(expected.isEmpty());

    }

}
