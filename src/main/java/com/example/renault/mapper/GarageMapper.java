package com.example.renault.mapper;

import com.example.renault.dto.GarageDTO;
import com.example.renault.dto.OpeningTimeDTO;
import com.example.renault.entities.Garage;
import com.example.renault.entities.OpeningTime;
import com.example.renault.entities.Openings;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GarageMapper {

    private final VehiculeMapper vehiculeMapper;

    public GarageMapper(VehiculeMapper vehiculeMapper) {
        this.vehiculeMapper = vehiculeMapper;
    }

    public GarageDTO toDTO(Garage garage) {
        Map<DayOfWeek, List<OpeningTimeDTO>> map = garage.getOpeningTimes().stream()
                .collect(Collectors.toMap(
                        Openings::getDayOfWeek, // suppose que Openings a un champ `DayOfWeek dayOfWeek`
                        openings -> openings.getOpeningTimes().stream()
                                .map(t -> new OpeningTimeDTO(t.getStartTime(), t.getEndTime()))
                                .toList()
                ));

        return new GarageDTO(garage.getId(),
                garage.getName(),
                garage.getAddress(),
                garage.getPhone(),
                garage.getEmail(),
                map,
                vehiculeMapper.toDTO(garage.getVehicules()));
    }

    public Garage toEntity(GarageDTO dto) {
        Garage g = new Garage();
        g.setId(dto.id());
        g.setName(dto.name());
        g.setPhone(dto.phone());
        g.setAddress(dto.address());
        g.setEmail(dto.email());
        dto.openingTimes().forEach((key, value) -> {
            Openings o = new Openings();
            o.setDayOfWeek(key);
            o.setOpeningTimes(value.stream()
                    .map(opening -> new OpeningTime(opening.startTime(), opening.endTime()))
                    .toList());
            g.addOpening(o);
        });
        return g;
    }
}
