package com.project.bolumYonetim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.Derslik;

@Repository
public interface DerslikRepository extends JpaRepository<Derslik,Long> {

}
