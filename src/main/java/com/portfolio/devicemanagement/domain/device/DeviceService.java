package com.portfolio.devicemanagement.domain.device;

import com.portfolio.devicemanagement.domain.reservation.ReservationRepository;
import com.portfolio.devicemanagement.domain.reservation.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final ReservationRepository reservationRepository;

    public List<DeviceEntity> find(DeviceSearchEntity searchEntity) {
        return deviceRepository.select(searchEntity);
    }

    public Optional<DeviceEntity> findById(long deviceId) {
        return deviceRepository.selectById(deviceId);
    }

    public List<ReservationEntity> findByDeviceAndPeriod(long deviseId, LocalDate start, LocalDate end) {
        return reservationRepository.findByDeviceAndPeriod(deviseId, start, end);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void register(DeviceEntity newEntity) {
        deviceRepository.insert(newEntity);
    }

    @Transactional
    public void update(DeviceEntity entity) {
        deviceRepository.update(entity);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void delete(long id) {
        deviceRepository.delete(id);
    }

}
