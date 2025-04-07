package com.example.bolumYonetim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bolumYonetim.model.DersProgrami;

@Repository
public interface DersProgramiRepository extends JpaRepository<DersProgrami,Long> {

}
