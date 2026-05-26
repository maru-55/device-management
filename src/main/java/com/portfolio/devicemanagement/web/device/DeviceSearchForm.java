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

        // ステータスをEnum(AVAILABLE, MAINTENANCE)に変換
        var statusEntityList = Optional.ofNullable(status())
                .map(statusList -> statusList.stream()
                        .filter(status -> !"USING".equals(status))
                        .map(DeviceStatus::valueOf)
                        .toList())
                .orElse(List.of());

        boolean searchUsing = Optional.ofNullable(status())
                .map(statusList -> statusList.contains("USING"))
                .orElse(false);

        return new DeviceSearchEntity(name(), statusEntityList, searchUsing);

    }

    public DeviceSearchDTO toDTO() {
        return new DeviceSearchDTO(name(), status());
    }

    public boolean isChecked(String value) {
        return status() != null && status.contains(value);
    }
}
