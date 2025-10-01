package com.example.renault.entities;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.List;

@Entity
public class Openings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ElementCollection
    @CollectionTable(name = "openings_opening_times",
            joinColumns = @JoinColumn(name = "opening_time_id"))
    private List<OpeningTime> openingTimes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<OpeningTime> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<OpeningTime> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
