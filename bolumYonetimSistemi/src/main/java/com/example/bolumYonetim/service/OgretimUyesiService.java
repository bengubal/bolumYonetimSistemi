package com.example.bolumYonetim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bolumYonetim.model.Derslik;
import com.example.bolumYonetim.model.OgretimUyesi;
import com.example.bolumYonetim.repository.DerslikRepository;
import com.example.bolumYonetim.repository.OgretimUyesiRepository;

@Service
public class OgretimUyesiService {
	
	final OgretimUyesiRepository ogretimUyesiRepository;

	@Autowired
	public OgretimUyesiService(OgretimUyesiRepository ogretimUyesiRepository) {
		this.ogretimUyesiRepository = ogretimUyesiRepository;
		
	}
	
	public List<OgretimUyesi> getAllOgr(){
		return ogretimUyesiRepository.findAll();
	}
	
	public OgretimUyesi saveOgr(OgretimUyesi ogretimUyesi) {
		return ogretimUyesiRepository.save(ogretimUyesi);
	}
	
	public void deleteOgr(Long id) {
		ogretimUyesiRepository.deleteById(id);
	}


}
