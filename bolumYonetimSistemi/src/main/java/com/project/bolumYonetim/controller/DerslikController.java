package com.project.bolumYonetim.controller;

import com.project.bolumYonetim.model.Derslik;
import com.project.bolumYonetim.service.DerslikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ders_derslik/derslik")
public class DerslikController {

    @Autowired
    private DerslikService derslikService;

    // Tüm derslikleri listele
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("derslikler", derslikService.getAllDerslik());
        return "dersProgrami/derslik-list"; // resources/templates/derslik-list.html
    }

    // Yeni derslik formu
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("derslik", new Derslik());
        model.addAttribute("derslikler", derslikService.getAllDerslik());
        return "dersProgrami/derslik-list";
    }

    // Kaydet (yeni veya güncelle)
    @PostMapping("/save")
    public String save(@ModelAttribute("derslik") Derslik derslik) {
        derslikService.saveDerslik(derslik);
        return "redirect:/ders_derslik/derslik";
    }

    // Güncelleme formu
    @GetMapping("/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Derslik derslik = derslikService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz ID: " + id));
        model.addAttribute("derslik", derslik);
        model.addAttribute("derslikler", derslikService.getAllDerslik());
        return "dersProgrami/derslik-list";
    }

    // Silme işlemi
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        derslikService.deleteDerslik(id);
        return "redirect:/ders_derslik/derslik";
    }
}