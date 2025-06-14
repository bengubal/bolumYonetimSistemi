package com.project.bolumYonetim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.model.User;

@Repository
public interface OgretimUyesiRepository extends JpaRepository<OgretimUyesi,Long> {

    Optional<OgretimUyesi> findByUser(User user);
    OgretimUyesi findByUserId(Long id);
    
}
