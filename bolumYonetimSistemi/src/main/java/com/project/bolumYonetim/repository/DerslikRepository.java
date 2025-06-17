package com.project.bolumYonetim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.Derslik;

@Repository
public interface DerslikRepository extends JpaRepository<Derslik,Long> {


    // Kapasitesi belirli bir değerden büyük olan derslikleri getir
    List<Derslik> findByKapasiteGreaterThan(int kapasite);


     Optional<Derslik> findByIsim(String isim);

}
