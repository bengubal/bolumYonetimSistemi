package com.project.bolumYonetim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bolumYonetim.model.DersProgrami;
import com.project.bolumYonetim.model.Notlar;
import com.project.bolumYonetim.model.OgretimUyesi;

public interface NotlarRepository extends JpaRepository<Notlar, Long> {

    List<Notlar> findByOgretimUyesi(OgretimUyesi ogretimUyesi);
    List<Notlar> findByDersProgrami(DersProgrami dersProgrami);
}