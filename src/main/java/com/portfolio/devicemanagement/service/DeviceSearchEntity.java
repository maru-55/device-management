package com.portfolio.devicemanagement.service;

import java.util.List;

public record DeviceSearchEntity(
        String name,
        List<DeviceStatus> status
) {
}
