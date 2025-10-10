package com.example.renault.config;

import com.example.renault.enums.FuelTypeEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CaseInsensitiveEnumConverter implements Converter<String, FuelTypeEnum> {
    @Override
    public FuelTypeEnum convert(@NonNull String source) {
        try {
            return FuelTypeEnum.valueOf(source.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid fuel type: " + source);
        }
    }
}
