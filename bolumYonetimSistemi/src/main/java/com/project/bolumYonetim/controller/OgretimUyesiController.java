package com.project.bolumYonetim.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.service.OgretimUyesiService;

@RestController
@RequestMapping("/api/ogretim-uyeleri")
public class OgretimUyesiController {

    @Autowired
    private OgretimUyesiService ogretimUyesiService;


    @GetMapping
    public List<OgretimUyesi> getAll() {
        return ogretimUyesiService.getAllOgr();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<OgretimUyesi> getById(@PathVariable Long id) {
        Optional<OgretimUyesi> ogretimUyesi = ogretimUyesiService.getById(id);
        if (ogretimUyesi.isPresent()) {
            return ResponseEntity.ok(ogretimUyesi.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

  
    @PostMapping
    public ResponseEntity<OgretimUyesi> create(@RequestBody OgretimUyesi ogretimUyesi) {
        try {
            OgretimUyesi createdOgretimUyesi = ogretimUyesiService.saveOgr(ogretimUyesi);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOgretimUyesi);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }

    // Öğretim üyesi güncelleme
    @PutMapping("/{id}")
    public ResponseEntity<OgretimUyesi> update(@PathVariable Long id, @RequestBody OgretimUyesi ogretimUyesi) {
        ogretimUyesi.setId(id);
        try {
            OgretimUyesi updatedOgretimUyesi = ogretimUyesiService.saveOgr(ogretimUyesi);
            return ResponseEntity.ok(updatedOgretimUyesi);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            ogretimUyesiService.deleteOgr(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }
}
