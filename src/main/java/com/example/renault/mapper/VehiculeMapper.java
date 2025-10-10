package com.example.renault.mapper;

import com.example.renault.dto.VehiculeDTO;
import com.example.renault.entities.VehiculeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccessoryMapper.class})
public interface VehiculeMapper {

    @Mapping(target = "garageId", source = "garage.id")
    @Mapping(target = "accessories", source = "accessories")
    VehiculeDTO toDTO(VehiculeEntity entity);

    @Mapping(target = "accessories", ignore = true)
    VehiculeEntity toEntity(VehiculeDTO dto);

    List<VehiculeDTO> toDTO(List<VehiculeEntity> vehiculeList);
}
