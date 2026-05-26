package com.portfolio.devicemanagement.domain.device;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record DeviceSearchRow(

        long id,
        String name,
        String modelNumber,
        String serialNumber,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate introductionDate,
        String location,
        DeviceStatus status,
        Long currentReservationId
) {
}
