package com.portfolio.devicemanagement.service;

import com.portfolio.devicemanagement.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public List<DeviceEntity> find(){
        return deviceRepository.select();
    }

    public Optional<DeviceEntity> findById(long deviceId) {

        return deviceRepository.selectById(deviceId);
    }
}
