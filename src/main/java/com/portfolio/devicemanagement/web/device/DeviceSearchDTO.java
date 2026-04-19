package com.portfolio.devicemanagement.web.device;

import java.util.List;
import java.util.Optional;

public record DeviceSearchDTO(
        String name,
        List<String> statusList
) {

    public boolean isChecked(String status) {
        return Optional.ofNullable(statusList)
                .map(l -> l.contains(status))
                .orElse(false);
    }
}
