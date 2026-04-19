package com.portfolio.devicemanagement.domain.reservation;

import com.portfolio.devicemanagement.domain.device.DeviceNotFoundException;
import com.portfolio.devicemanagement.web.device.DeviceForm;
import com.portfolio.devicemanagement.domain.device.DeviceRepository;
import com.portfolio.devicemanagement.domain.device.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final DeviceRepository deviceRepository;
    private final ReservationRepository reservationRepository;
    private final DeviceService deviceService;

    // 予約データの取得
    public List<ReservationEntity> findByDeviceAndPeriod(long deviseId, LocalDate start, LocalDate end) {
        return reservationRepository.findByDeviceAndPeriod(deviseId, start, end);
    }

    @Transactional
    public void createReservation(long deviceId, ReservationEntity reservationEntity) {

        //端末のステータス更新(->RENTED)
        var deviceForm = deviceService.findById(deviceId)
                .map(DeviceForm::fromEntity)
                .orElseThrow(DeviceNotFoundException::new);
        var deviceEntity = deviceForm.toEntity(deviceId, "RENTED");
        deviceService.update(deviceEntity);

        // 予約データ追加
        reservationRepository.insert(reservationEntity);

    }
}
