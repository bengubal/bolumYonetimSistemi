package com.project.bolumYonetim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bolumYonetim.model.Ders;
import com.project.bolumYonetim.service.DersService;

@RestController
@RequestMapping("/api/dersler")
public class DersController {
	 @Autowired
	    private DersService dersService;

	    @GetMapping
	    public List<Ders> getAll() {
	        return dersService.getAll();
	    }
	    
	    @GetMapping("/{id}")
	    public Optional<Ders> getById(@PathVariable Long id) {
	        return dersService.getById(id);
	    }

	    @PostMapping
	    public Ders create(@RequestBody Ders ders) {
	        return dersService.save(ders);
	    }

	    @PutMapping("/{id}")
	    public Ders update(@PathVariable Long id, @RequestBody Ders ders) {
	        ders.setId(id); // güncellenecek id’yi set et
	        return dersService.save(ders);
	    }

	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        dersService.delete(id);
	    }

}
