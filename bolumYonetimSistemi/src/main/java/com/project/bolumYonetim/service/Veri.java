package com.project.bolumYonetim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.repository.UserRepository;

@Component
public class Veri implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) { // Eğer hiç kullanıcı yoksa ekle
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("adminpass");
            admin.setRole("Bölüm Başkanı");
            userRepository.save(admin);

            User sekreter = new User();
            sekreter.setUsername("sekreter");
            sekreter.setPassword("sekreterpass");
            sekreter.setRole("Bölüm Sekreteri");
            userRepository.save(sekreter);

            User ogretimElemani = new User();
            ogretimElemani.setUsername("ogretim_elemani");
            ogretimElemani.setPassword("ogretimpass");
            ogretimElemani.setRole("Öğretim Elemanı");
            userRepository.save(ogretimElemani);

            System.out.println("Ön tanımlı kullanıcılar eklendi.");
        }
    }
}
