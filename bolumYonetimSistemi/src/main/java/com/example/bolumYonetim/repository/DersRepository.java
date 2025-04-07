package com.example.bolumYonetim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bolumYonetim.model.Ders;

@Repository
public interface DersRepository extends JpaRepository<Ders,Long>{

}
