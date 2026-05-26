package com.portfolio.devicemanagement.domain.reservation;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    RESERVED("予約中"),
    CANCELLED("キャンセル");

    private final String label;

    ReservationStatus(String label) {
        this.label = label;
    }

}
