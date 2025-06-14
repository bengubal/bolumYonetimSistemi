package com.project.bolumYonetim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller 
public class LoginController {
    
    @Autowired
    private UserService userService;

    // Ana giriş sayfası
    @GetMapping("/")
    public String index() {
        return "yetkilendirme/index";
    }

    // İdari giriş ekranı
    @GetMapping("/idariGiris")
    public String idariGiris() {
        return "yetkilendirme/idariGiris";
    }

    // Akademik giriş ekranı
    @GetMapping("/akademikGiris")
    public String akademikGiris() {
        return "yetkilendirme/akademikGiris";
    }

    // İdari giriş işlemi
    @PostMapping("/idariLogin")
    public String idariLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {

            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            if("Bölüm Başkanı".equals(user.getRole()))
            {
                return "yetkilendirme/bolumBaskani";
            }
            else if ("Bölüm Sekreteri".equals(user.getRole()))
            {
                return "yetkilendirme/bolumSekreteri";
            }
        }

        model.addAttribute("error", "Geçersiz giriş veya yetkisiz kullanıcı.");
        return "yetkilendirme/idariGiris";
    }

    // Akademik giriş işlemi
    @PostMapping("/akademikLogin")
    public String akademikLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {

            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            if("Bölüm Başkanı".equals(user.getRole()))
            {
                return "yetkilendirme/bolumBaskani";
            }
            else if ("Öğretim Elemanı".equals(user.getRole()))
            {
                return "yetkilendirme/ogretimElemani";
            }
        }

        model.addAttribute("error", "Geçersiz giriş veya yetkisiz kullanıcı.");
        return "yetkilendirme/akademikGiris";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
