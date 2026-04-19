package com.portfolio.devicemanagement.web.reservation;

import com.portfolio.devicemanagement.domain.reservation.ReservationEntity;

import java.time.LocalDate;
public record ReservationDTO (
        Long id,
        Long deviceId,
        Long userId,
        LocalDate startDate,
        LocalDate endDate
){
    public static ReservationDTO toDTO(ReservationEntity entity) {

        return new ReservationDTO(
                entity.id(),
                entity.deviceId(),
                entity.userId(),
                entity.startDate(),
                entity.endDate()
        );
    }
}
