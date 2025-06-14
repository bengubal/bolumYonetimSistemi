package com.project.bolumYonetim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bolumYonetim.model.DersProgrami;
import com.project.bolumYonetim.model.Notlar;
import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.service.DersProgramiService;
import com.project.bolumYonetim.service.NotlarService;
import com.project.bolumYonetim.service.OgretimUyesiService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/notlar")
public class NotlarController {
    
    @Autowired
    private NotlarService notlarService;

    @Autowired
    private OgretimUyesiService ogretimUyesiService;

    @Autowired
    private DersProgramiService dersProgramiService;

    @GetMapping("/ogretimUyesi")
    public String ogretimUyesiNotlar(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
    OgretimUyesi ogretimUyesi = ogretimUyesiService.findByUserId(user.getId());
    List<DersProgrami> program = dersProgramiService.getByOgrUye(ogretimUyesi);
    List<Notlar> notlar = notlarService.getNotlarByOgretimUyesi(ogretimUyesi);

    model.addAttribute("ogretimUyesi", ogretimUyesi);
    model.addAttribute("program", program);
    model.addAttribute("notlar", notlar);
        return "dersProgrami/kapiIsimligi";
    }

    @PostMapping("/ekle")
    public String notEkle(@RequestParam Long dersProgramiId,
                          @RequestParam String notMetni,
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        OgretimUyesi ogretimUyesi = ogretimUyesiService.findByUserId(user.getId());
        DersProgrami dersProgrami = dersProgramiService.getById(dersProgramiId).orElseThrow();

        Notlar not = new Notlar();
        not.setDersProgrami(dersProgrami);
        not.setOgretimUyesi(ogretimUyesi);
        not.setNotMetni(notMetni);

        notlarService.saveNot(not);

        return "redirect:/notlar/ogretimUyesi";
    }

    @GetMapping("/yonetici")
    public String yoneticiNotlar(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String role = user.getRole();

        if ("Bölüm Başkanı".equals(role) || "Bölüm Sekreteri".equals(role)) {
            List<Notlar> notlar = notlarService.getAllNotlar();
            model.addAttribute("notlar", notlar);
            return "dersprogrami/yoneticiNotlar"; // Yöneticiler için Thymeleaf sayfası
        }

        return "redirect:/"; // Yetkisizse ana sayfaya
    }
}
