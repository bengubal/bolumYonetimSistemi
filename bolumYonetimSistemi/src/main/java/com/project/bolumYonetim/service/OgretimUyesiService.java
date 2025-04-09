package com.project.bolumYonetim.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bolumYonetim.model.Derslik;
import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.repository.DerslikRepository;
import com.project.bolumYonetim.repository.OgretimUyesiRepository;

@Service
public class OgretimUyesiService {
	
	final OgretimUyesiRepository ogretimUyesiRepository;

	@Autowired
	public OgretimUyesiService(OgretimUyesiRepository ogretimUyesiRepository) {
		this.ogretimUyesiRepository = ogretimUyesiRepository;
		
	}
	
	 public List<OgretimUyesi> getAllOgr() {
	        return ogretimUyesiRepository.findAll();
	    }


	    public OgretimUyesi saveOgr(OgretimUyesi ogretimUyesi) {
	        return ogretimUyesiRepository.save(ogretimUyesi);
	    }


	    public void deleteOgr(Long id) {
	        ogretimUyesiRepository.deleteById(id);
	    }


	    public Optional<OgretimUyesi> getById(Long id) {
	        return ogretimUyesiRepository.findById(id); // Retrieves the Optional of OgretimUyesi
	    }

}
