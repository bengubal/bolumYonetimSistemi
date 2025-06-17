package com.project.bolumYonetim.repository;

import com.project.bolumYonetim.model.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {
    
    // Öğrenci numarasına göre öğrenci bulma
    Optional<Ogrenci> findByOgrenciNo(String ogrenciNo);
    
    // Ad ve soyadına göre arama
    List<Ogrenci> findByAdContainingIgnoreCaseAndSoyadContainingIgnoreCase(String ad, String soyad);
    

    // Birden fazla ID'ye göre öğrencileri getirme
    @Query("SELECT o FROM Ogrenci o WHERE o.id IN :ids")
    List<Ogrenci> findByIdIn(@Param("ids") List<Long> ids);
    
    // E-mail adresine göre öğrenci bulma
    Optional<Ogrenci> findByEmail(String email);
    
    // Ad veya soyada göre arama (LIKE)
    @Query("SELECT o FROM Ogrenci o WHERE o.ad LIKE %:searchTerm% OR o.soyad LIKE %:searchTerm%")
    List<Ogrenci> searchByAdOrSoyad(@Param("searchTerm") String searchTerm);
    

}
