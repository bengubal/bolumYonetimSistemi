package com.example.bolumYonetim.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ders")
public class Ders {
	
	
	
	@Id
	Long id;
	String ders_kodu;
	String ders_adi;
	Long akts;
	Long kredi;
	Long kontenjan;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDers_kodu() {
		return ders_kodu;
	}
	public void setDers_kodu(String ders_kodu) {
		this.ders_kodu = ders_kodu;
	}
	public String getDers_adi() {
		return ders_adi;
	}
	public void setDers_adi(String ders_adi) {
		this.ders_adi = ders_adi;
	}
	public Long getAkts() {
		return akts;
	}
	public void setAkts(Long akts) {
		this.akts = akts;
	}
	public Long getKredi() {
		return kredi;
	}
	public void setKredi(Long kredi) {
		this.kredi = kredi;
	}
	public Long getKontenjan() {
		return kontenjan;
	}
	public void setKontenjan(Long kontenjan) {
		this.kontenjan = kontenjan;
	}

	
	
    
}
