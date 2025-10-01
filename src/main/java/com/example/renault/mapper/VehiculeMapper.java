package com.example.renault.mapper;
import com.example.renault.dto.VehiculeDTO;
import com.example.renault.entities.Vehicule;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehiculeMapper {

    private final AccessoryMapper accessoryMapper;

    public VehiculeMapper(AccessoryMapper accessoryMapper){
        this.accessoryMapper = accessoryMapper;
    }

    public VehiculeDTO toDTO(Vehicule vehicule) {
        return new VehiculeDTO(
                vehicule.getId(),
                vehicule.getBrand(),
                vehicule.getFabricationDate(),
                vehicule.getFuelType(),
                vehicule.getGarage() != null ? vehicule.getGarage().getId() : null,
                accessoryMapper.toDTO(vehicule.getAccessories())
        );
    }

    public Vehicule toEntity(VehiculeDTO dto) {
        Vehicule v = new Vehicule();
        v.setId(dto.id());
        v.setBrand(dto.brand());
        v.setFabricationDate(dto.fabricationDate());
        v.setFuelType(dto.fuelType());
        return v;
    }
    public List<VehiculeDTO> toDTO(List<Vehicule> vehicules) {
       if(CollectionUtils.isEmpty(vehicules)) return new ArrayList<>();
       return vehicules.stream().map(this::toDTO).toList();
    }
}
