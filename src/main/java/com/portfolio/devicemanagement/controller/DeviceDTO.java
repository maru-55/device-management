package com.portfolio.devicemanagement.controller;

import com.portfolio.devicemanagement.service.DeviceEntity;
import com.portfolio.devicemanagement.service.DeviceStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DeviceDTO (
        long id,
        String name,
        String modelNumber,
        String serialNumber,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate introductionDate,
        String location,
        String statusCode,
        String statusLabel
){
    public static DeviceDTO toDTO(DeviceEntity entity) {

        //String(DB) -> Enum -> String(日本語ラベル）
        String statusLabel = entity.status().getLabel();

        return new DeviceDTO(
                entity.id(),
                entity.name(),
                entity.modelNumber(),
                entity.serialNumber(),
                entity.introductionDate(),
                entity.location(),
                entity.status().name(),
                statusLabel

        );
    }
}
