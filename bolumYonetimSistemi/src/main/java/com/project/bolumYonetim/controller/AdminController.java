package com.project.bolumYonetim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"Bölüm Başkanı".equals(role)) {
            return "yetkilendirme/yetkisiz";
        }

        model.addAttribute("users", userService.getAllUsers());
        return "yetkilendirme/kullaniciYonetimi"; // Kullanıcıları listeleyen sayfa
    }

    @GetMapping("/addUser")
    public String addUserForm(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"Bölüm Başkanı".equals(role)) {
            return "yetkilendirme/yetkisiz";
        }

        model.addAttribute("user", new User());
        return "yetkilendirme/kullaniciYonetimi";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/users"; // Kullanıcı eklenince listeye geri dön
    }

    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable Long id, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"Bölüm Başkanı".equals(role)) {
            return "yetkilendirme/yetkisiz";
        }

        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "yetkilendirme/kullaniciYonetimi";
    }


        @PostMapping("/editUser")
    public String editUser(@ModelAttribute User user) {
        // Mevcut kullanıcıyı veritabanından çekiyoruz
        User existingUser = userService.getUserById(user.getId());

        // Eğer yeni şifre girilmemişse, eski şifreyi kullan
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        }

        userService.saveUser(user);
        return "redirect:/admin/users";
    }


    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"Bölüm Başkanı".equals(role)) {
            return "yetkilendirme/yetkisiz";
        }

        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
    
}
