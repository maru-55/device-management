package com.portfolio.devicemanagement.web.reservation;

import com.portfolio.devicemanagement.domain.reservation.ReservationEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReservationForm(
        Long deviceId,
        @NotNull(message = "利用開始日を選択してください")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,
        @NotNull(message = "利用終了日を選択してください")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate

) {
        public static ReservationForm fromEntity(ReservationEntity reservationEntity) {
                return new ReservationForm(
                        reservationEntity.deviceId(),
                        reservationEntity.startDate(),
                        reservationEntity.endDate()
                );
        }

        public ReservationEntity toEntity() {
                return new ReservationEntity(
                        null,
                        null,
                        null,
                        startDate(),
                        endDate());
        }

        public ReservationEntity toEntity(long deviceId, long userId) {
                return new ReservationEntity(
                        null,
                        deviceId,
                        userId,
                        startDate(),
                        endDate());
        }
}
