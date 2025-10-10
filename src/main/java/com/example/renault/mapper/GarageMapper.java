package com.example.renault.mapper;

import com.example.renault.dto.GarageDTO;
import com.example.renault.dto.OpeningTimeDTO;
import com.example.renault.entities.GarageEntity;
import com.example.renault.entities.OpeningTimeEntity;
import com.example.renault.entities.OpeningsEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {VehiculeMapper.class})
public interface GarageMapper {

    @Mapping(target = "vehicules", source = "vehicules")
    @Mapping(target = "openingTimes", source = "openingTimes")
    GarageDTO toDTO(GarageEntity entity);

    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "openingTimes", source = "openingTimes")
    GarageEntity toEntity(GarageDTO dto);

    default Map<DayOfWeek, List<OpeningTimeDTO>> mapOpeningTimeList(List<OpeningsEntity> openingTimes) {
        if(openingTimes == null) return null;
        return openingTimes.stream()
                .collect(Collectors.toMap(
                        OpeningsEntity::getDayOfWeek,
                        openings -> openings.getOpeningTimes().stream()
                                .map(t -> new OpeningTimeDTO(t.getStartTime(), t.getEndTime()))
                                .toList()
                ));
    }

    default List<OpeningsEntity> mapOpeningTimeList(Map<DayOfWeek, List<OpeningTimeDTO>> openingTimes) {
        if(openingTimes == null) return null;
        return openingTimes.entrySet().stream()
                .map(entry -> {
                    OpeningsEntity o = new OpeningsEntity();
                    o.setDayOfWeek(entry.getKey());
                    o.setOpeningTimes(entry.getValue().stream()
                            .map(opening -> new OpeningTimeEntity(opening.startTime(), opening.endTime()))
                            .toList());
                    return o;
        }).toList();
    }

    @AfterMapping
    default void linkGarage(@MappingTarget GarageEntity garage) {
        if (garage.getOpeningTimes() != null) {
            garage.getOpeningTimes().forEach(o -> o.setGarage(garage));
        }
    }
}
