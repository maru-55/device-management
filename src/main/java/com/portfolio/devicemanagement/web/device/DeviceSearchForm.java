package com.portfolio.devicemanagement.web.device;

import com.portfolio.devicemanagement.domain.device.DeviceSearchEntity;
import com.portfolio.devicemanagement.domain.device.DeviceStatus;

import java.util.List;
import java.util.Optional;

public record DeviceSearchForm(
        String name,
        List<String> status
) {

    public DeviceSearchEntity toEntity() {
        var statusEntityList = Optional.ofNullable(status())
                .map(statusList -> statusList.stream().map(DeviceStatus::valueOf).toList())
                .orElse(List.of());

        return new DeviceSearchEntity(name(), statusEntityList);

    }

    public DeviceSearchDTO toDTO() {
        return new DeviceSearchDTO(name(), status());
    }
}
