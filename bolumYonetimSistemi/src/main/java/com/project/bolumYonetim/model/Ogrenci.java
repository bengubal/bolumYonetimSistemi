package com.project.bolumYonetim.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ogrenci")
public class Ogrenci {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String ad;
    
    @Column(nullable = false)
    private String soyad;
    
    @Column(unique = true, nullable = false)
    private String ogrenciNo;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String telefon;
    private String bolum;
    private Integer sinif;
    
    // Constructors
    public Ogrenci() {}
    
    public Ogrenci(String ad, String soyad, String ogrenciNo, String email) {
        this.ad = ad;
        this.soyad = soyad;
        this.ogrenciNo = ogrenciNo;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    
    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }
    
    public String getOgrenciNo() { return ogrenciNo; }
    public void setOgrenciNo(String ogrenciNo) { this.ogrenciNo = ogrenciNo; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    
    public String getBolum() { return bolum; }
    public void setBolum(String bolum) { this.bolum = bolum; }
    
    public Integer getSinif() { return sinif; }
    public void setSinif(Integer sinif) { this.sinif = sinif; }
}