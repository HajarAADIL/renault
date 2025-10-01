package com.example.renault.mapper;

import com.example.renault.dto.AccessoryDTO;
import com.example.renault.entities.Accessory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccessoryMapper {
    public AccessoryDTO toDTO(Accessory accessory) {
        return new AccessoryDTO(
                accessory.getId(),
                accessory.getName(),
                accessory.getDescription(),
                accessory.getPrice(),
                accessory.getType(),
                accessory.getVehicule() != null ? accessory.getVehicule().getId() : null
        );
    }

    public Accessory toEntity(AccessoryDTO dto) {
        Accessory a = new Accessory();
        a.setId(dto.id());
        a.setName(dto.name());
        a.setDescription(dto.description());
        a.setPrice(dto.price());
        a.setType(dto.type());
        return a;
    }
    public List<AccessoryDTO> toDTO(List<Accessory> accessorys) {
        if(CollectionUtils.isEmpty(accessorys)) return new ArrayList<>();
        return accessorys.stream().map(this::toDTO).toList();
    }
}
