package com.portfolio.devicemanagement.controller;

import com.portfolio.devicemanagement.controller.admin.DeviceForm;
import com.portfolio.devicemanagement.service.DeviceEntity;
import com.portfolio.devicemanagement.service.DeviceSearchEntity;
import com.portfolio.devicemanagement.service.DeviceService;
import com.portfolio.devicemanagement.service.DeviceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public String list(DeviceSearchForm searchForm, Model model) {
            var deviceList = deviceService.find(searchForm.toEntity())
                    .stream()
                    .map(DeviceDTO::toDTO)
                    .toList();
        model.addAttribute("deviceList", deviceList);
        model.addAttribute("searchDTO", searchForm.toDTO());
        return "devices/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long deviceId, Model model) {
        var deviceDTO = deviceService.findById(deviceId)
                .map(DeviceDTO::toDTO)
                .orElseThrow(DeviceNotFoundException::new);
        model.addAttribute("device", deviceDTO);
        return "devices/detail";
    }

    @PostMapping("/borrow/{id}")
    public String borrow(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        var form = deviceService.findById(id)
                .map(DeviceForm::fromEntity)
                .orElseThrow(DeviceNotFoundException::new);
        var entity = form.toEntity(id, "RENTED");
        deviceService.update(entity);
            redirectAttributes.addFlashAttribute("message", "貸出処理が完了しました");
        return "redirect:/devices";
    }
}