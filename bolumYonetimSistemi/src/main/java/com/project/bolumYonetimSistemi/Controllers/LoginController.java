package com.project.bolumYonetimSistemi.Controllers;

import com.project.bolumYonetimSistemi.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bolumYonetimSistemi.Services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller 
public class LoginController {
    
    @Autowired
    private UserService userService;

    // Ana giriş sayfası
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // İdari giriş ekranı
    @GetMapping("/idariGiris")
    public String idariGiris() {
        return "idariGiris";
    }

    // Akademik giriş ekranı
    @GetMapping("/akademikGiris")
    public String akademikGiris() {
        return "akademikGiris";
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
                return "bolumBaskani";
            }
            else if ("Bölüm Sekreteri".equals(user.getRole()))
            {
                return "bolumSekreteri";
            }
        }

        model.addAttribute("error", "Geçersiz giriş veya yetkisiz kullanıcı.");
        return "idariGiris";
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
                return "bolumBaskani";
            }
            else if ("Öğretim Elemanı".equals(user.getRole()))
            {
                return "ogretimElemani";
            }
        }

        model.addAttribute("error", "Geçersiz giriş veya yetkisiz kullanıcı.");
        return "akademikGiris";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
