package com.project.bolumYonetim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.bolumYonetim.model.SinavProgrami;
import com.project.bolumYonetim.service.DersService;
import com.project.bolumYonetim.service.DerslikService;
import com.project.bolumYonetim.service.OgretimUyesiService;
import com.project.bolumYonetim.service.SinavProgramiService;

@Controller
@RequestMapping("/sinavprogrami")
public class SinavProgramiViewController {
    
    @Autowired
    private SinavProgramiService sinavProgramiService;

    @Autowired
    private DersService dersService;

    @Autowired
    private DerslikService derslikService;

    @Autowired
    private OgretimUyesiService ogretimUyesiService;

    @GetMapping
    public String sinavSayfasi(Model model) {
        model.addAttribute("sinavlar", sinavProgramiService.getAll());
        model.addAttribute("sinavProgrami", new SinavProgrami());
        model.addAttribute("dersler", dersService.getAll());
        model.addAttribute("derslikler", derslikService.getAllDerslik());
        model.addAttribute("ogretimUyeleri", ogretimUyesiService.getAllOgr());
        return "sinavprogrami/index";
    }

    @PostMapping("/kaydet")
    public String sinavKaydet(@ModelAttribute("sinavProgrami") SinavProgrami sinav, Model model) {
        try {
            sinavProgramiService.save(sinav);
            return "redirect:/sinavprogrami";
        } catch (IllegalArgumentException e) {
            model.addAttribute("sinavlar", sinavProgramiService.getAll());
            model.addAttribute("sinavProgrami", sinav);
            model.addAttribute("dersler", dersService.getAll());
            model.addAttribute("derslikler", derslikService.getAllDerslik());
            model.addAttribute("ogretimUyeleri", ogretimUyesiService.getAllOgr());
            model.addAttribute("hataMesaji", e.getMessage());
            return "sinavprogrami/index";
        }
    }


    @GetMapping("/sil/{id}")
    public String silSinav(@PathVariable Long id) {
        sinavProgramiService.delete(id);
        return "redirect:/sinavprogrami";
    }
}
