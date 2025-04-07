package com.example.bolumYonetim.model;

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
	String baslangic_saati;
	String bitis_saati;
	
	
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

	public String getBaslangic_saati() {
		return baslangic_saati;
	}

	public void setBaslangic_saati(String baslangic_saati) {
		this.baslangic_saati = baslangic_saati;
	}

	public String getBitis_saati() {
		return bitis_saati;
	}

	public void setBitis_saati(String bitis_saati) {
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
