package com.example.bolumYonetim.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bolumYonetim.model.DersProgrami;
import com.example.bolumYonetim.repository.DersProgramiRepository;


@Service
public class DersProgramiService {
	
	final DersProgramiRepository dersProgramiRepository;
	
	@Autowired
	public DersProgramiService(DersProgramiRepository dersProgramiRepository) {
	        this.dersProgramiRepository = dersProgramiRepository;
	}
	
	public List<DersProgrami> getAll() {
        return dersProgramiRepository.findAll();
    }

    public DersProgrami save(DersProgrami dersProgrami) {
        return dersProgramiRepository.save(dersProgrami);
    }

    public void delete(Long id) {
        dersProgramiRepository.deleteById(id);
    }
    
    public Optional<DersProgrami> getById(Long id) {
       return dersProgramiRepository.findById(id);
       }


 


}
