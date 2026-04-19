package com.portfolio.devicemanagement.web.device;

import com.portfolio.devicemanagement.domain.auth.CustomUserDetails;
import com.portfolio.devicemanagement.domain.device.DeviceNotFoundException;
import com.portfolio.devicemanagement.domain.device.DeviceService;
import com.portfolio.devicemanagement.domain.reservation.ReservationService;
import com.portfolio.devicemanagement.web.reservation.ReservationDTO;
import com.portfolio.devicemanagement.web.reservation.ReservationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final ReservationService reservationService;

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

    // カレンダー画面（HTML）を表示する用
    @GetMapping("/{id}/reserveForm")
    public String showReservationForm(
            @PathVariable Long id,
            @ModelAttribute ReservationForm form,
            Model model) {
        var deviceDTO = deviceService.findById(id)
                .map(DeviceDTO::toDTO)
                .orElseThrow(DeviceNotFoundException::new);
        model.addAttribute("device", deviceDTO);
        model.addAttribute("reservationForm", form);
        return "devices/reservationForm";
    }

    // カレンダーに塗るデータを返す用
    @GetMapping("/{id}/reservations") // パスを分ける
    @ResponseBody
    public List<Map<String, Object>> getEvents(@PathVariable Long id) {
        LocalDate start = LocalDate.now().minusMonths(3);
        LocalDate end = LocalDate.now().plusMonths(3);

        var reservationList = deviceService.findByDeviceAndPeriod(id, start, end)
                .stream()
                .map(ReservationDTO::toDTO)
                .toList();

        return reservationList.stream().map(r -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", "貸出中");
            event.put("start", r.startDate());
            event.put("end", r.endDate().plusDays(1));
            event.put("color", "red");
            event.put("display", "block");
            return event;
        }).toList();
    }

    @PostMapping("/reserve/{id}")
    public String createReservation(
            @PathVariable("id") long deviceId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Validated ReservationForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        if(bindingResult.hasErrors()){
            return showReservationForm(deviceId, form, model);
        }

        try {
            reservationService.createReservation(deviceId, form.toEntity(deviceId, user.getUserId()));
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/devices/borrow/" + deviceId;
        }
        redirectAttributes.addFlashAttribute("message", "貸出処理が完了しました");
        return "redirect:/devices";
    }

}