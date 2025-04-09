package com.project.bolumYonetim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.DersProgrami;

@Repository
public interface DersProgramiRepository extends JpaRepository<DersProgrami,Long> {
    List<DersProgrami> findByGun(String gun);
}
