package com.project.bolumYonetim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.DersProgrami;
import com.project.bolumYonetim.model.OgretimUyesi;

@Repository
public interface DersProgramiRepository extends JpaRepository<DersProgrami,Long> {
    List<DersProgrami> findByGun(String gun);
    List<DersProgrami> findAll();
    @Query("SELECT dp FROM DersProgrami dp JOIN FETCH dp.ders")
    List<DersProgrami> findAllWithDers();
    List<DersProgrami> findByOgretimuyesi(OgretimUyesi ogretimUyesi);
}
