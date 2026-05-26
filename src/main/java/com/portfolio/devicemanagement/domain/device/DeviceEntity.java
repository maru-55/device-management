package com.portfolio.devicemanagement.domain.device;

import java.time.LocalDate;

public record DeviceEntity (

        Long id,
        String name,
        String modelNumber,
        String serialNumber,
        LocalDate introductionDate,
        String location,
        DeviceStatus status
){
}
