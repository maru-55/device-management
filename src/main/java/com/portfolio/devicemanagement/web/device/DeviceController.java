package com.portfolio.devicemanagement.web.device;

import com.portfolio.devicemanagement.domain.auth.CustomUserDetails;
import com.portfolio.devicemanagement.domain.device.DeviceNotFoundException;
import com.portfolio.devicemanagement.domain.device.DeviceSearchEntity;
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

    // 端末一覧を表示
    @GetMapping
    public String list(DeviceSearchForm searchForm, Model model) {

        var deviceList = deviceService.find(searchForm.toEntity());

        var dtoList = deviceList.stream()
                        .map(DeviceDTO::toDTO)
                        .toList();

        model.addAttribute("deviceList", dtoList);
        model.addAttribute("searchDTO", searchForm.toDTO());

        return "devices/list";
    }

    // 端末詳細を表示
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
//            @ModelAttribute ReservationForm form,
            Model model) {

        var deviceDTO = deviceService.findById(id)
                .map(DeviceDTO::toDTO)
                .orElseThrow(DeviceNotFoundException::new);

        model.addAttribute("device", deviceDTO);
        if (!model.containsAttribute("reservationForm")) {
            model.addAttribute("reservationForm", new ReservationForm(null,null, null, null));
        }

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
            LocalDate today = LocalDate.now();

            // 今日の日付が開始日～終了日の期間に含まれている場合、「貸出中」と表示する
            boolean isStartDate = today.isAfter(r.startDate()) || today.isEqual(r.startDate());
            boolean isEndDate = today.isBefore(r.endDate()) || today.isEqual(r.endDate());

            String title = "予約中";
            String color = "blue";

            // 終了日が今日より前の場合（貸出履歴）
            if (r.endDate().isBefore(today)) {
                title = "利用終了";
                color = "gray";

            // 開始日～終了日の期間に今日が含まれている場合
            } else if (isStartDate && isEndDate) {
                title = "貸出中";
                color = "red";

            // 開始日・終了日が今日より先の日付の場合（予約）
            } else {
                title = "予約中";
                color = "blue";
            }

            event.put("title", title);
            event.put("start", r.startDate());
            event.put("end", r.endDate().plusDays(1));
            event.put("color", color);
            event.put("display", "block");
            return event;
        }).toList();

    }

    // 予約機能
    @PostMapping("/reserve/{id}")
    public String createReservation(
            @PathVariable("id") long deviceId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Validated ReservationForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

       if(bindingResult.hasErrors()){
           redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "reservationForm", bindingResult);
           redirectAttributes.addFlashAttribute("reservationForm", form);
           redirectAttributes.addFlashAttribute("errorMessage", "入力内容に不備があります");
           return "redirect:/devices/" + deviceId + "/reserveForm";
        }

        try {
            reservationService.createReservation(deviceId, form.toEntity(deviceId, user.getUserId()));
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/devices/" + deviceId + "/reserveForm";
        }
        redirectAttributes.addFlashAttribute("successMessage", "貸出処理が完了しました");
        return "redirect:/devices/" + deviceId + "/reserveForm";
    }

    // 予約のキャンセル機能
    public void cancelReservation(Long deviceId, RedirectAttributes redirectAttributes) {

    }
}