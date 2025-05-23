package com.project.bolumYonetim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.bolumYonetim.model.DersProgrami;
import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.repository.OgretimUyesiRepository;
import com.project.bolumYonetim.service.DersProgramiService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ogretimelemani")
public class DersProgramiOgretimController {
    
    @Autowired
    private DersProgramiService dersProgramiService;

    @Autowired
    private OgretimUyesiRepository ogretimUyesiRepository;

    @GetMapping("/kapiIsimligi")
    public String getKapiIsimligi(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<OgretimUyesi> uyeOpt = ogretimUyesiRepository.findByUser(user);

        if (uyeOpt.isEmpty()) {
            model.addAttribute("error", "Öğretim üyesi bilgisi bulunamadı.");
            return "error";
        }

        OgretimUyesi uye = uyeOpt.get();
        List<DersProgrami> program = dersProgramiService.getByOgrUye(uye);

        model.addAttribute("ogretimUyesi", uye);
        model.addAttribute("program", program);

        return "dersProgrami/kapiIsimligi";
    }

}
