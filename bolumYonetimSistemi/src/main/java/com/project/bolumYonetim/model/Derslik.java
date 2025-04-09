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
	public void setKapasite(Long kapasite) {
		this.kapasite = kapasite;
	}

	
	
}
