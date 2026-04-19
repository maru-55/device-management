package com.portfolio.devicemanagement.web.admin;

import com.portfolio.devicemanagement.domain.device.DeviceNotFoundException;
import com.portfolio.devicemanagement.domain.device.DeviceService;
import com.portfolio.devicemanagement.web.device.DeviceForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/devices")
public class AdminDeviceController {

    private final DeviceService deviceService;

    @GetMapping("/registerForm")
    public String showRegisterForm(@ModelAttribute DeviceForm form, Model model) {
        model.addAttribute("mode", "REGISTER");
        return "admin/devices/form";
    }

    @PostMapping
    public String register(@Validated DeviceForm form, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return showRegisterForm(form, model);
        }
        deviceService.register(form.toEntity());
        return "redirect:/devices";
    }

    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        var form = deviceService.findById(id)
                .map(DeviceForm::fromEntity)
                        .orElseThrow(DeviceNotFoundException::new);
        model.addAttribute("deviceForm", form);
        model.addAttribute("mode", "EDIT");
        return "admin/devices/form";
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable("id") long id,
            @Validated @ModelAttribute DeviceForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if(bindingResult.hasErrors()){
            model.addAttribute("mode", "EDIT");
            return "admin/devices/form";
        }
        var entity = form.toEntity(id);
        deviceService.update(entity);
        return "redirect:/devices/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id){
        deviceService.delete(id);
        return "redirect:/devices";
    }
}

