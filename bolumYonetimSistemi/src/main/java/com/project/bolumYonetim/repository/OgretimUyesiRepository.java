package com.project.bolumYonetim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.OgretimUyesi;

@Repository
public interface OgretimUyesiRepository extends JpaRepository<OgretimUyesi,Long> {

}
