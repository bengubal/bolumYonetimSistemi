package com.project.bolumYonetim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bolumYonetim.model.Derslik;
import com.project.bolumYonetim.repository.DerslikRepository;

@Service
public class DerslikService {
	
	final DerslikRepository derslikRepository;
	
	@Autowired
	public DerslikService(DerslikRepository derslikRepository) {
		this.derslikRepository = derslikRepository;
		
	}
	
	public List<Derslik> getAllDerslik(){
		return derslikRepository.findAll();
	}
	
	public Derslik saveDerslik(Derslik derslik) {
		return derslikRepository.save(derslik);
	}
	
	public void deleteDerslik(Long id) {
		derslikRepository.deleteById(id);
	}


}
