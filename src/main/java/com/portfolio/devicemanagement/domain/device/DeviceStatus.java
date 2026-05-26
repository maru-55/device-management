package com.portfolio.devicemanagement.domain.device;

import lombok.Getter;

@Getter
public enum DeviceStatus {
    AVAILABLE("貸出可"),
    MAINTENANCE("メンテナンス中");

    private final String label;

    DeviceStatus(String label) {
        this.label = label;
    }

}
