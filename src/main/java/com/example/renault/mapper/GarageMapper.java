package com.example.renault.mapper;

import com.example.renault.dto.GarageDTO;
import com.example.renault.dto.OpeningTimeDTO;
import com.example.renault.entities.GarageEntity;
import com.example.renault.entities.OpeningTimeEntity;
import com.example.renault.entities.OpeningsEntity;
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

    public GarageDTO toDTO(GarageEntity garage) {
        Map<DayOfWeek, List<OpeningTimeDTO>> map = garage.getOpeningTimes().stream()
                .collect(Collectors.toMap(
                        OpeningsEntity::getDayOfWeek, // suppose que Openings a un champ `DayOfWeek dayOfWeek`
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

    public GarageEntity toEntity(GarageDTO dto) {
        GarageEntity g = new GarageEntity();
        g.setId(dto.id());
        g.setName(dto.name());
        g.setPhone(dto.phone());
        g.setAddress(dto.address());
        g.setEmail(dto.email());
        dto.openingTimes().forEach((key, value) -> {
            OpeningsEntity o = new OpeningsEntity();
            o.setDayOfWeek(key);
            o.setOpeningTimes(value.stream()
                    .map(opening -> new OpeningTimeEntity(opening.startTime(), opening.endTime()))
                    .toList());
            g.addOpening(o);
        });
        return g;
    }
}
