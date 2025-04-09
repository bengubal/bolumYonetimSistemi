package com.project.bolumYonetim.controller;

import com.project.bolumYonetim.model.DersProgrami;
import com.project.bolumYonetim.service.DersProgramiService;
import com.project.bolumYonetim.service.DersService;
import com.project.bolumYonetim.service.DerslikService;
import com.project.bolumYonetim.service.OgretimUyesiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/dersProgrami")
public class DersProgramiViewController {

private final DersService dersService;
private final OgretimUyesiService ogretimUyesiService;
private final DerslikService derslikService;
private final DersProgramiService dersProgramiService;

@Autowired
public DersProgramiViewController(
        DersProgramiService dersProgramiService,
        DersService dersService,
        OgretimUyesiService ogretimUyesiService,
        DerslikService derslikService) {
    this.dersProgramiService = dersProgramiService;
    this.dersService = dersService;
    this.ogretimUyesiService = ogretimUyesiService;
    this.derslikService = derslikService;
}

    @GetMapping
    public String showDersProgramiList(Model model) {
        List<DersProgrami> dersProgramlari = dersProgramiService.getAll();
        model.addAttribute("dersProgramlari", dersProgramlari);
        return "dersProgrami/list";
    }

    @GetMapping("/yeni")
    public String showAddForm(Model model) {
        model.addAttribute("dersProgrami", new DersProgrami());
        model.addAttribute("dersler", dersService.getAll()); // dersService eklenmeli
        model.addAttribute("ogretimUyeleri", ogretimUyesiService.getAllOgr()); // ogretimUyesiService eklenmeli
        model.addAttribute("derslikler", derslikService.getAllDerslik()); // derslikService eklenmeli
        return "dersProgrami/form";
    }
    
    @GetMapping("/duzenle/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DersProgrami dersProgrami = dersProgramiService.getById(id).orElseThrow();
        model.addAttribute("dersProgrami", dersProgrami);
        model.addAttribute("dersler", dersService.getAll());
        model.addAttribute("ogretimUyeleri", ogretimUyesiService.getAllOgr());
        model.addAttribute("derslikler", derslikService.getAllDerslik());
        return "dersProgrami/form";
    }
    

    @PostMapping("/kaydet")
    public String saveDersProgrami(@ModelAttribute DersProgrami dersProgrami) {
        dersProgramiService.save(dersProgrami);
        return "redirect:/web/dersProgrami";
    }


    @GetMapping("/sil/{id}")
    public String deleteDersProgrami(@PathVariable Long id) {
        dersProgramiService.delete(id);
        return "redirect:/web/dersProgrami";
    }

    
}
