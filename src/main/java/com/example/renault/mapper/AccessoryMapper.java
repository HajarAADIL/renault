package com.example.renault.mapper;

import com.example.renault.dto.AccessoryDTO;
import com.example.renault.entities.AccessoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccessoryMapper {

    @Mapping(target = "vehiculeId", source = "vehicule.id")
    AccessoryDTO toDTO(AccessoryEntity entity);

    AccessoryEntity toEntity(AccessoryDTO dto);

    List<AccessoryDTO> toDTO(List<AccessoryEntity> accessoryList);

}
