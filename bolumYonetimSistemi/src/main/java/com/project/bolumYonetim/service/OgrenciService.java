package com.project.bolumYonetim.service;

import com.project.bolumYonetim.model.Ogrenci;
import com.project.bolumYonetim.repository.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OgrenciService {

    @Autowired
    private OgrenciRepository ogrenciRepository;

    // Tüm öğrencileri getir
    public List<Ogrenci> getAllOgrenci() {
        return ogrenciRepository.findAll();
    }

    // ID'ye göre öğrenci getir
    public Optional<Ogrenci> getById(Long id) {
        return ogrenciRepository.findById(id);
    }

    // Birden fazla ID'ye göre öğrencileri getir
    public List<Ogrenci> getByIds(List<Long> ids) {
        return ogrenciRepository.findByIdIn(ids);
    }

    // Öğrenci kaydet
    public Ogrenci saveOgrenci(Ogrenci ogrenci) {
        return ogrenciRepository.save(ogrenci);
    }

    // Öğrenci güncelle
    public Ogrenci updateOgrenci(Long id, Ogrenci ogrenciDetails) {
        Ogrenci ogrenci = ogrenciRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Öğrenci bulunamadı: " + id));

        ogrenci.setOgrenciNo(ogrenciDetails.getOgrenciNo());
        ogrenci.setAd(ogrenciDetails.getAd());
        ogrenci.setSoyad(ogrenciDetails.getSoyad());
        ogrenci.setEmail(ogrenciDetails.getEmail());
        ogrenci.setBolum(ogrenciDetails.getBolum());
        ogrenci.setSinif(ogrenciDetails.getSinif());
     

        return ogrenciRepository.save(ogrenci);
    }

    // Öğrenci sil
    public void deleteOgrenci(Long id) {
        if (!ogrenciRepository.existsById(id)) {
            throw new IllegalArgumentException("Öğrenci bulunamadı: " + id);
        }
        ogrenciRepository.deleteById(id);
    }

    // Öğrenci numarasına göre öğrenci bul
    public Optional<Ogrenci> findByOgrenciNo(String ogrenciNo) {
        return ogrenciRepository.findByOgrenciNo(ogrenciNo);
    }


    // Ad veya soyada göre arama
    public List<Ogrenci> searchOgrenci(String searchTerm) {
        return ogrenciRepository.searchByAdOrSoyad(searchTerm);
    }

    // E-mail adresine göre öğrenci bul
    public Optional<Ogrenci> findByEmail(String email) {
        return ogrenciRepository.findByEmail(email);
    }
    
    // Öğrenci var mı kontrol et
    public boolean existsById(Long id) {
        return ogrenciRepository.existsById(id);
    }

    // Öğrenci numarası benzersiz mi kontrol et
    public boolean isOgrenciNoUnique(String ogrenciNo, Long excludeId) {
        Optional<Ogrenci> existing = ogrenciRepository.findByOgrenciNo(ogrenciNo);
        return existing.isEmpty() || existing.get().getId().equals(excludeId);
    }

    // E-mail benzersiz mi kontrol et
    public boolean isEmailUnique(String email, Long excludeId) {
        Optional<Ogrenci> existing = ogrenciRepository.findByEmail(email);
        return existing.isEmpty() || existing.get().getId().equals(excludeId);
    }
}