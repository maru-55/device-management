package com.portfolio.devicemanagement.service;

import com.portfolio.devicemanagement.controller.DeviceNotFoundException;
import com.portfolio.devicemanagement.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public List<DeviceEntity> find(DeviceSearchEntity searchEntity) {
        return deviceRepository.select(searchEntity);
    }

    public Optional<DeviceEntity> findById(long deviceId) {

        return deviceRepository.selectById(deviceId);
    }

    @Transactional
    public void register(DeviceEntity newEntity) {
        deviceRepository.insert(newEntity);
    }

    @Transactional
    public void update(DeviceEntity entity) {
        deviceRepository.update(entity);
    }

    @Transactional
    public void delete(long id) {
        deviceRepository.delete(id);
    }

 /*   @Transactional
    public void borrow(long id) {
        DeviceEntity entity = deviceRepository.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException("対象の端末が見つかりませんでした。ID:" + id));

        if(!"AVAILABLE".equals(entity.status().name())){
            throw new IllegalStateException("選択した端末は現在貸出できない状態です。(現在の状態: " + device.status().getLabel() + ")");
        }

        entity.status() =DeviceStatus.valueOf("RENTED");
        deviceRepository.updateStatus(device);
    }
*/
}
