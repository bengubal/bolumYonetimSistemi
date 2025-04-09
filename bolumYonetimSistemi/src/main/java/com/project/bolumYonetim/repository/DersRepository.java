package com.project.bolumYonetim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bolumYonetim.model.Ders;

@Repository
public interface DersRepository extends JpaRepository<Ders,Long>{

}
