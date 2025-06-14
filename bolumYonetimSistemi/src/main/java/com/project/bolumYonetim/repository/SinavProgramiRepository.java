package com.project.bolumYonetim.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.Derslik;
import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.model.SinavProgrami;

@Repository
public interface SinavProgramiRepository  extends JpaRepository<SinavProgrami ,Long> {
    List<SinavProgrami> findByTarih(LocalDate tarih);
    List<SinavProgrami> findByDersId(Long dersId);
    List<SinavProgrami> findByDerslikId(Long derslikId);
    List<SinavProgrami> findByGozetmenId(Long gozetmenId);
    List<SinavProgrami> findByTarihAndSaatAndDerslik(LocalDate tarih, LocalTime saat, Derslik derslik);
    List<SinavProgrami> findByTarihAndSaatAndGozetmen(LocalDate tarih, LocalTime saat, OgretimUyesi gozetmen);
    List<SinavProgrami> findByGozetmen(OgretimUyesi gozetmen);
}
