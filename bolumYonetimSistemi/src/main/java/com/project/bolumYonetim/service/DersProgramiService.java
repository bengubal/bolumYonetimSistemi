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
        return dersProgramiRepository.save(dersProgrami);
    }

    // Ders programını sil
    public void delete(Long id) {
        dersProgramiRepository.deleteById(id);
    }
}
