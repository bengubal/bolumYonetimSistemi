// SinavOturmaDuzeniRepository.java
package com.project.bolumYonetim.repository;

import com.project.bolumYonetim.model.SinavOturmaDuzeni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SinavOturmaDuzeniRepository extends JpaRepository<SinavOturmaDuzeni, Long> {
    
    // Oluşturma tarihine göre azalan sırada getir
    @Query("SELECT s FROM SinavOturmaDuzeni s ORDER BY s.olusturmaTarihi DESC")
    List<SinavOturmaDuzeni> findAllOrderByOlusturmaTarihiDesc();
    
    @Query("SELECT s FROM SinavOturmaDuzeni s WHERE s.sinavAdi = :sinavAdi AND s.sinavTarihi = :sinavTarihi")
    List<SinavOturmaDuzeni> findConflictingExams(@Param("sinavAdi") String sinavAdi, @Param("sinavTarihi") LocalDateTime sinavTarihi);

    
    // Filtreleme (derslik adı ve duruma göre)
    @Query("SELECT s FROM SinavOturmaDuzeni s WHERE " +
           "(:derslikAdi IS NULL OR s.derslikAdi = :derslikAdi) AND " +
           "(:durum IS NULL OR s.durum = :durum)")
    List<SinavOturmaDuzeni> findByFilters(@Param("derslikAdi") String derslikAdi, 
                                         @Param("durum") String durum);
    
    // Derslik adına göre getir
    List<SinavOturmaDuzeni> findByDerslikAdi(String derslikAdi);
    
    // Duruma göre getir
    List<SinavOturmaDuzeni> findByDurum(String durum);
    
    // Onaylanmış oturma düzenlerini getir
    List<SinavOturmaDuzeni> findByOnaylandiTrue();
    
    // Sınav adına göre getir
    List<SinavOturmaDuzeni> findBySinavAdi(String sinavAdi);
    
    // Onaylanmamış oturma düzenlerini getir
    List<SinavOturmaDuzeni> findByOnaylandiFalse();
    

    


}