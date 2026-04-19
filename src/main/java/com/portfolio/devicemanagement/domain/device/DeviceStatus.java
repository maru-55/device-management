package com.portfolio.devicemanagement.domain.device;

import lombok.Getter;

@Getter
public enum DeviceStatus {
    AVAILABLE("在庫有"),
    RENTED("貸出中"),
    MAINTENANCE("メンテナンス中");

    private final String label;

    DeviceStatus(String label) {
        this.label = label;
    }

}
