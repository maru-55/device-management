package com.portfolio.devicemanagement.service;

import lombok.Getter;

@Getter
public enum DeviceStatus {

    AVAILABLE("在庫有"),
    RENTED("貸出中"),
    BROKEN("故障中"),
    DISPOSED("廃棄"),
    MAINTENANCE("メンテナンス中");

    private final String label;

    DeviceStatus(String label) {
        this.label = label;
    }

}
