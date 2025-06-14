package com.project.bolumYonetim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bolumYonetim.model.Ders;
import com.project.bolumYonetim.repository.DersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DersService {
	
	 final DersRepository dersRepository;
	 
	 @Autowired
	 public DersService(DersRepository dersRepository) {
		 this.dersRepository = dersRepository;		 
	 }
	 
	 public List<Ders> getAll(){
		 return dersRepository.findAll();
	 }
	 
	 
	 public Optional<Ders> getById(Long id) {
		    return dersRepository.findById(id);
		}

	 
	 public Ders save(Ders ders) {
		 return dersRepository.save(ders);
	 }
	 
	 public void delete(Long id) {
		 dersRepository.deleteById(id);
	 }
	 
	 
	

}
