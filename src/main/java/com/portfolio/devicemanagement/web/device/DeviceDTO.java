package com.portfolio.devicemanagement.web.device;

import com.portfolio.devicemanagement.domain.device.DeviceEntity;
import com.portfolio.devicemanagement.domain.device.DeviceSearchRow;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

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
    public static DeviceDTO toDTO(DeviceSearchRow row) {

        //String(DB) -> Enum -> String(日本語ラベル）
        String statusLabel = row.status().getLabel();

        if(row.currentReservationId() != null){
            statusLabel = "貸出中";
        }

        return new DeviceDTO(
                row.id(),
                row.name(),
                row.modelNumber(),
                row.serialNumber(),
                row.introductionDate(),
                row.location(),
                row.status().name(),
                statusLabel
        );
    }
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
