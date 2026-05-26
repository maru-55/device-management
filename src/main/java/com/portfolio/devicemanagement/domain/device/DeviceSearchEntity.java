package com.portfolio.devicemanagement.domain.device;

import java.util.List;

public record DeviceSearchEntity(
        String name,
        List<DeviceStatus> status,
        boolean searchUsing
) {
}
