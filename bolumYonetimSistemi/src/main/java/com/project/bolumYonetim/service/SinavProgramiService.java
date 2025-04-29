package com.project.bolumYonetim.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bolumYonetim.model.SinavProgrami;
import com.project.bolumYonetim.repository.SinavProgramiRepository;

@Service
public class SinavProgramiService {
    
    private final SinavProgramiRepository sinavProgramiRepository;

    @Autowired
    public SinavProgramiService(SinavProgramiRepository sinavProgramiRepository){
        this.sinavProgramiRepository = sinavProgramiRepository;
    }

    public List<SinavProgrami> getAll(){
        return sinavProgramiRepository.findAll();
    }

    public Optional<SinavProgrami> getById(Long id){
        return sinavProgramiRepository.findById(id);
    }

    public List<SinavProgrami> getByTarih(LocalDate tarih){
        return sinavProgramiRepository.findByTarih(tarih);
    }

    public List<SinavProgrami> getByDersId(Long dersId){
        return sinavProgramiRepository.findByDersId(dersId);
    }

    public List<SinavProgrami> getByDerslikId(Long derslikId) {
        return sinavProgramiRepository.findByDerslikId(derslikId);
    }

    public List<SinavProgrami> getByGozetmenId(Long gozetmenId) {
        return sinavProgramiRepository.findByGozetmenId(gozetmenId);
    }

    public SinavProgrami save(SinavProgrami sinavProgrami) {
        // Aynı tarih ve saatte aynı derslikte sınav var mı?
        List<SinavProgrami> derslikCakismalari = sinavProgramiRepository
            .findByTarihAndSaatAndDerslik(sinavProgrami.getTarih(), sinavProgrami.getSaat(), sinavProgrami.getDerslik());
    
        if (!derslikCakismalari.isEmpty()) {
            throw new IllegalArgumentException("Bu tarihte ve saatte bu derslikte başka bir sınav var!");
        }
    
        // Aynı gözetmen başka sınavda mı?
        List<SinavProgrami> gozetmenCakismalari = sinavProgramiRepository
            .findByTarihAndSaatAndGozetmen(sinavProgrami.getTarih(), sinavProgrami.getSaat(), sinavProgrami.getGozetmen());
    
        if (!gozetmenCakismalari.isEmpty()) {
            throw new IllegalArgumentException("Bu tarihte ve saatte bu gözetmen başka bir sınavda görevli!");
        }
    
        return sinavProgramiRepository.save(sinavProgrami);
    }

    public void delete(Long id) {
        sinavProgramiRepository.deleteById(id);
    }
}
