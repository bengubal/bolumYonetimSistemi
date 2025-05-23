package com.project.bolumYonetim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.model.SinavProgrami;
import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.repository.OgretimUyesiRepository;
import com.project.bolumYonetim.repository.SinavProgramiRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ogretimelemani/sinavprogrami")
public class SinavProgramiOgretimController {

    @Autowired
    private OgretimUyesiRepository ogretimUyesiRepository;

    @Autowired
    private SinavProgramiRepository sinavProgramiRepository;

    @GetMapping
    public String ogretimElemaniSinavProgrami(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null ) {
            return "redirect:/akademikGiris";
        }

        Optional<OgretimUyesi> uyeOpt = ogretimUyesiRepository.findByUser(user);
        if (uyeOpt.isEmpty()) {
            model.addAttribute("error", "Öğretim üyesi bulunamadı.");
            return "error";
        }

        OgretimUyesi uye = uyeOpt.get();

        List<SinavProgrami> sinavlar = sinavProgramiRepository.findByGozetmenId(uye.getId());

        model.addAttribute("sinavlar", sinavlar);
        return "sinavprogrami/ogretimElemani";
    }
}
