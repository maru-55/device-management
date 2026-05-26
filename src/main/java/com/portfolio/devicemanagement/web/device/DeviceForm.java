package com.portfolio.devicemanagement.web.device;

import com.portfolio.devicemanagement.domain.device.DeviceEntity;
import com.portfolio.devicemanagement.domain.device.DeviceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DeviceForm(
        @NotBlank
        @Size(max = 256, message="256文字以内で入力して下さい")
        String name,
        String modelNumber,

        String serialNumber,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate introductionDate,
        String location,
        @NotBlank
        @Pattern(regexp="AVAILABLE|MAINTENANCE", message="貸出可, メンテナンス中 のいずれかを選択してください")
        String status

) {
        public static DeviceForm fromEntity(DeviceEntity deviceEntity) {
                return new DeviceForm(
                        deviceEntity.name(),
                        deviceEntity.modelNumber(),
                        deviceEntity.serialNumber(),
                        deviceEntity.introductionDate(),
                        deviceEntity.location(),
                        deviceEntity.status().name()
                );
        }

        public DeviceEntity toEntity() {
                return new DeviceEntity(
                        null,
                        name(),
                        modelNumber(),
                        serialNumber(),
                        introductionDate(),
                        location(),
                        DeviceStatus.valueOf(status()));
        }
        public DeviceEntity toEntity(long id) {
                return new DeviceEntity(
                        id,
                        name(),
                        modelNumber(),
                        serialNumber(),
                        introductionDate(),
                        location(),
                        DeviceStatus.valueOf(status()));
        }
        public DeviceEntity toEntity(long id, String status) {
                return new DeviceEntity(
                        id,
                        name(),
                        modelNumber(),
                        serialNumber(),
                        introductionDate(),
                        location(),
                        DeviceStatus.valueOf(status));
        }
}
