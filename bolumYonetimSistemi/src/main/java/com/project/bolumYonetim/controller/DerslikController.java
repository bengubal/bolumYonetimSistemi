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

import com.project.bolumYonetim.model.Derslik;
import com.project.bolumYonetim.service.DerslikService;

@RestController
@RequestMapping("/api/derslikler")
public class DerslikController {

    @Autowired
    private DerslikService derslikService;

    // Get all Derslik entries
    @GetMapping
    public List<Derslik> getAll() {
        return derslikService.getAllDerslik();
    }

    // Get a Derslik entry by ID
    @GetMapping("/{id}")
    public Optional<Derslik> getById(@PathVariable Long id) {
        return derslikService.getAllDerslik().stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    // Create a new Derslik entry
    @PostMapping
    public Derslik create(@RequestBody Derslik derslik) {
        return derslikService.saveDerslik(derslik);
    }

    // Update an existing Derslik entry
    @PutMapping("/{id}")
    public Derslik update(@PathVariable Long id, @RequestBody Derslik derslik) {
        derslik.setId(id); // Update the ID for the correct entry
        return derslikService.saveDerslik(derslik);
    }

    // Delete a Derslik entry by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        derslikService.deleteDerslik(id);
    }
}
