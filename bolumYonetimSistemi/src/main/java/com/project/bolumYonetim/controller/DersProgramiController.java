package com.project.bolumYonetim.controller;

import com.project.bolumYonetim.model.DersProgrami;

import com.project.bolumYonetim.service.DersProgramiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dersProgrami")
public class DersProgramiController {

    private final DersProgramiService dersProgramiService;

    @Autowired
    public DersProgramiController(DersProgramiService dersProgramiService) {
        this.dersProgramiService = dersProgramiService;
    }

    // Tüm ders programlarını al
    @GetMapping
    public List<DersProgrami> getAllDersProgrami() {
        return dersProgramiService.getAll();
    }

    // Belirli bir gün için ders programını al
    @GetMapping("/gun/{gun}")
    public List<DersProgrami> getDersProgramiByGun(@PathVariable String gun) {
        return dersProgramiService.getByGun(gun);
    }

    // Ders programı ekle
    @PostMapping
    public DersProgrami addDersProgrami(@RequestBody DersProgrami dersProgrami) {
        return dersProgramiService.save(dersProgrami);
    }

    // Ders programını güncelle
    @PutMapping("/{id}")
    public DersProgrami updateDersProgrami(@PathVariable Long id, @RequestBody DersProgrami dersProgrami) {
        dersProgrami.setId(id);
        return dersProgramiService.save(dersProgrami);
    }

    // Ders programını sil
    @DeleteMapping("/{id}")
    public void deleteDersProgrami(@PathVariable Long id) {
        dersProgramiService.delete(id);
    }
}