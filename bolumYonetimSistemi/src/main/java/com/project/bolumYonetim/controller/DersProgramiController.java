package com.project.bolumYonetim.controller;

import com.project.bolumYonetim.model.DersProgrami;

import com.project.bolumYonetim.service.DersProgramiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dersProgrami")
public class DersProgramiController {

    private final DersProgramiService dersProgramiService;

    @Autowired
    public DersProgramiController(DersProgramiService dersProgramiService) {
        this.dersProgramiService = dersProgramiService;
    }

    @GetMapping
    public String getAllDersProgrami(Model model) {
        List<DersProgrami> dersProgramlari = dersProgramiService.getAll();
        model.addAttribute("dersProgramlari", dersProgramlari);
        return "index";
    }

    @GetMapping("/dersEkle")
    public String addDersProgramiForm(Model model) {
        model.addAttribute("dersProgrami", new DersProgrami());
        return "dersEkle";
    }

    @PostMapping("/dersEkle")
    public String addDersProgrami(@ModelAttribute DersProgrami dersProgrami) {
        dersProgramiService.save(dersProgrami);
        return "redirect:/dersProgrami";
    }

    @GetMapping("/dersDuzenle/{id}")
    public String editDersProgrami(@PathVariable Long id, Model model) {
        DersProgrami dersProgrami = dersProgramiService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid DersProgrami ID:" + id));
        model.addAttribute("dersProgrami", dersProgrami);
        return "dersDuzenle";
    }

    @PostMapping("/dersDuzenle/{id}")
    public String updateDersProgrami(@PathVariable Long id, @ModelAttribute DersProgrami dersProgrami) {
        dersProgrami.setId(id);
        dersProgramiService.save(dersProgrami);
        return "redirect:/dersProgrami";
    }

    @GetMapping("/dersSil/{id}")
    public String deleteDersProgrami(@PathVariable Long id) {
        dersProgramiService.delete(id);
        return "redirect:/dersProgrami";
    }
}