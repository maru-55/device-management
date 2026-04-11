package com.portfolio.devicemanagement.controller;

import com.portfolio.devicemanagement.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/devices")
    public String list(Model model) {
        var deviceList = deviceService.find()
                .stream()
                        .map(DeviceDTO::toDTO)
                        .toList();
        model.addAttribute("deviceList", deviceList);
        return "devices/list";
    }

    @GetMapping("/devices/{id}")
    public String showDetail(@PathVariable("id") long deviceId, Model model) {
        var deviceEntity = deviceService.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("No device found with id: " + deviceId));
        model.addAttribute("device", DeviceDTO.toDTO(deviceEntity));
        return "devices/detail";
    }
}
