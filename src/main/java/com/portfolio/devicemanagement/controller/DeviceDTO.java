package com.portfolio.devicemanagement.controller;

import com.portfolio.devicemanagement.service.DeviceEntity;
import com.portfolio.devicemanagement.service.DeviceStatus;

public record DeviceDTO (
        long id,
        String name,
        String status
){
    public static DeviceDTO toDTO(DeviceEntity entity) {

        //String(DB) -> Enum -> String(日本語ラベル）
        String label = DeviceStatus.valueOf(entity.status()).getLabel();

        return new DeviceDTO(
                entity.id(),
                entity.name(),
                label
        );
    }
}
