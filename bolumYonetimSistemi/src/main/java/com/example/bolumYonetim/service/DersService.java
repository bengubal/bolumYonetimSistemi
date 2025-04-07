package com.example.bolumYonetim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bolumYonetim.model.Ders;
import com.example.bolumYonetim.repository.DersRepository;
import java.util.List;

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
	 
	 public Ders save(Ders ders) {
		 return dersRepository.save(ders);
	 }
	 
	 public void delete(Long id) {
		 dersRepository.deleteById(id);
	 }
	 
	 
	

}
