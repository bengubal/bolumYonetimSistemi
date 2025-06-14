package com.project.bolumYonetim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bolumYonetim.model.Notlar;
import com.project.bolumYonetim.model.OgretimUyesi;
import com.project.bolumYonetim.repository.NotlarRepository;

@Service
public class NotlarService {
    @Autowired
    private NotlarRepository notlarRepository;

    public List<Notlar> getNotlarByOgretimUyesi(OgretimUyesi ogretimUyesi) {
        return notlarRepository.findByOgretimUyesi(ogretimUyesi);
    }

    public List<Notlar> getAllNotlar() {
        return notlarRepository.findAll();
    }

    public Notlar saveNot(Notlar not) {
        return notlarRepository.save(not);
    }
}
