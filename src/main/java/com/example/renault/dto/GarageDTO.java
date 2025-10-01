package com.example.renault.dto;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public record GarageDTO(Long id,
                        String name,
                        String address,
                        String phone,
                        String email,
                        Map<DayOfWeek, List<OpeningTimeDTO>> openingTimes,
                        List<VehiculeDTO> vehicules) {
}
