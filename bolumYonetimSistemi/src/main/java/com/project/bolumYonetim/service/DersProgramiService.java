package com.project.bolumYonetim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bolumYonetim.model.DersProgrami;
import com.project.bolumYonetim.repository.DersProgramiRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DersProgramiService {

    private final DersProgramiRepository dersProgramiRepository;

    @Autowired
    public DersProgramiService(DersProgramiRepository dersProgramiRepository) {
        this.dersProgramiRepository = dersProgramiRepository;
    }

    // Tüm ders programlarını al
    public List<DersProgrami> getAll() {
        return dersProgramiRepository.findAll();
    }

    public List<DersProgrami> getAllWithDers() {
        return dersProgramiRepository.findAllWithDers();
    }

    // Belirli bir gün için ders programını al
    public List<DersProgrami> getByGun(String gun) {
        return dersProgramiRepository.findByGun(gun);
    }
    
    public Optional<DersProgrami> getById(Long id) {
        return dersProgramiRepository.findById(id);
    }

    // Ders programı ekle
    public DersProgrami save(DersProgrami dersProgrami) {
        List<DersProgrami> mevcutProgramlar = dersProgramiRepository.findByGun(dersProgrami.getGun());
    
        for (DersProgrami mevcut : mevcutProgramlar) {
            boolean zamanCakismasi = 
                !(dersProgrami.getBitis_saati().isBefore(mevcut.getBaslangic_saati()) ||
                  dersProgrami.getBaslangic_saati().isAfter(mevcut.getBitis_saati()));
    
            if (zamanCakismasi) {
                // Aynı öğretim üyesi aynı saatte mi?
                if (mevcut.getOgretimuyesi().getId().equals(dersProgrami.getOgretimuyesi().getId())) {
                    throw new IllegalArgumentException("Aynı saatte bu öğretim üyesine başka bir ders atanamaz!");
                }
    
                // Aynı derslik aynı saatte mi?
                if (mevcut.getDerslik().getId().equals(dersProgrami.getDerslik().getId())) {
                    throw new IllegalArgumentException("Aynı saatte bu derslik başka bir derste kullanılıyor!");
                }
            }
        }
    
        return dersProgramiRepository.save(dersProgrami);
    }
    

    // Ders programını sil
    public void delete(Long id) {
        dersProgramiRepository.deleteById(id);
    }
}
