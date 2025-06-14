package com.project.bolumYonetim.controller;

import com.project.bolumYonetim.model.Ders;
import com.project.bolumYonetim.model.Derslik;
import com.project.bolumYonetim.service.DersService;
import com.project.bolumYonetim.service.DerslikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ders_derslik")
public class DersIslemleriController {

    private final DersService dersService;
    private final DerslikService derslikService;

    @Autowired
    public DersIslemleriController(DersService dersService, DerslikService derslikService) {
        this.dersService = dersService;
        this.derslikService = derslikService;
    }

    // Ders işlemleri
    @GetMapping("/dersler")
    public String listDersler(Model model) {
        List<Ders> dersler = dersService.getAll();
        model.addAttribute("dersler", dersler);
        return "dersProgrami/dersislemleri"; // Thymeleaf şablonu
    }

    @GetMapping("/ders/{id}")
    public String getDers(@PathVariable("id") Long id, Model model) {
        Optional<Ders> ders = dersService.getById(id);
        if (ders.isPresent()) {
            model.addAttribute("ders", ders.get());
            return "dersProgrami/dersislemleri"; // Thymeleaf şablonu
        }
        return "redirect:/ders_derslik/dersler"; // Ders bulunmazsa listeye dön
    }

    @GetMapping("/ders/new")
    public String showAddDersForm(Model model) {
        model.addAttribute("ders", new Ders());
        return "dersProgrami/dersislemleri"; // Yeni ders formu
    }

    @PostMapping("/ders/save")
    public String saveDers(@ModelAttribute("ders") Ders ders) {
        dersService.save(ders);
        return "redirect:/ders_derslik/dersler"; // Kaydedildikten sonra dersler sayfasına dön
    }

    @GetMapping("/ders/delete/{id}")
    public String deleteDers(@PathVariable("id") Long id) {
        dersService.delete(id);
        return "redirect:/ders_derslik/dersler"; // Silme işlemi sonrası dersler sayfasına dön
    }


}
