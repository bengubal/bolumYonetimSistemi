package com.project.bolumYonetim.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="dersprogrami")
public class DersProgrami {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String gun;


	LocalTime baslangic_saati;
	LocalTime bitis_saati;
	
	
	@ManyToOne
	@JoinColumn(name="ders_id")
	Ders ders;
	
	@ManyToOne
	@JoinColumn(name="ogr_uye_id")
	OgretimUyesi ogretimuyesi;
	
	@ManyToOne
	@JoinColumn(name="derslik_id")
	Derslik derslik;
	

	
	   public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getGun() {
	        return gun;
	    }

	    public void setGun(String gun) {
	        this.gun = gun;
	    }

	    public LocalTime getBaslangic_saati() {  // Added getter method
	        return baslangic_saati;
	    }

	    public void setBaslangic_saati(LocalTime baslangic_saati) {
	        this.baslangic_saati = baslangic_saati;
	    }

	    public LocalTime getBitis_saati() {  // Added getter method
	        return bitis_saati;
	    }

	    public void setBitis_saati(LocalTime bitis_saati) {
	        this.bitis_saati = bitis_saati;
	    }

	    public Ders getDers() {
	        return ders;
	    }

	    public void setDers(Ders ders) {
	        this.ders = ders;
	    }

	    public OgretimUyesi getOgretimuyesi() {
	        return ogretimuyesi;
	    }

	    public void setOgretimuyesi(OgretimUyesi ogretimuyesi) {
	        this.ogretimuyesi = ogretimuyesi;
	    }

	    public Derslik getDerslik() {
	        return derslik;
	    }

	    public void setDerslik(Derslik derslik) {
	        this.derslik = derslik;
	    }

}
