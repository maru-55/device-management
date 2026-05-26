package com.portfolio.devicemanagement.domain.reservation;

import java.time.LocalDate;

public record ReservationEntity (
        Long id,
        Long deviceId,
        Long userId,
        LocalDate startDate,
        LocalDate endDate,
        ReservationStatus status
) {

}
