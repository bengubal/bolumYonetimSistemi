package com.project.bolumYonetim.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name="derslik")
public class Derslik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String isim;
    Long kapasite;
    Long genislik;
    Long yukseklik;
    
    @Override
    public String toString() {
        return this.id.toString();
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIsim() {
        return isim;
    }
    
    public void setIsim(String isim) {
        this.isim = isim;
    }

    public Long getKapasite() {
        return kapasite;
    }

    // Kapasiteyi doğrudan set etmek yerine private yaptık
    private void setKapasite(Long kapasite) {
        this.kapasite = kapasite;
    }
    
    public Long getGenislik() {
        return genislik;
    }
    
    public void setGenislik(Long genislik) {
        this.genislik = genislik;
        updateKapasite();
    }
    
    public Long getYukseklik() {
        return yukseklik;
    }
    
    public void setYukseklik(Long yukseklik) {
        this.yukseklik = yukseklik;
        updateKapasite();
    }

    private void updateKapasite() {
        if (this.genislik != null && this.yukseklik != null) {
            this.kapasite = this.genislik * this.yukseklik;
        } else {
            this.kapasite = null;
        }
    }

    public String getBoyutString() {
        if (genislik != null && yukseklik != null) {
            return genislik + " x " + yukseklik;
        }
        return "Boyut belirtilmemiş";
    }
}
