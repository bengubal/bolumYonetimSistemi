package com.project.bolumYonetim.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "sinav_oturma_duzeni")
public class SinavOturmaDuzeni {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String sinavAdi;
    
    @Column(nullable = false)
    private LocalDateTime sinavTarihi;

    @Column(nullable = false)
    private String derslikAdi;
    
    @Column(nullable = false)
    private String gozetmenler;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sinav_oturma_duzeni_id")
    private List<Ogrenci> ogrenciler;
    
    @ElementCollection
    @CollectionTable(name = "oturma_pozisyonlari", joinColumns = @JoinColumn(name = "sinav_oturma_duzeni_id"))
    @MapKeyColumn(name = "pozisyon")
    @Column(name = "ogrenci_bilgisi")
    private Map<String, String> oturmaPozisyonlari;
    
    @Column(nullable = false)
    private LocalDateTime olusturmaTarihi;
    
    private LocalDateTime onayTarihi;
    
    @Column(nullable = false)
    private String durum;
    
    @Column(nullable = false)
    private boolean onaylandi;
    
    // Constructors
    public SinavOturmaDuzeni() {}
    
    public SinavOturmaDuzeni(String sinavAdi, LocalDateTime sinavTarihi, String derslikAdi, 
                             String gozetmenler, List<Ogrenci> ogrenciler) {
        this.sinavAdi = sinavAdi;
        this.sinavTarihi = sinavTarihi;
        this.derslikAdi = derslikAdi;
        this.gozetmenler = gozetmenler;
        this.ogrenciler = ogrenciler;
        this.olusturmaTarihi = LocalDateTime.now();
        this.durum = "TASLAK";
        this.onaylandi = false;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSinavAdi() { return sinavAdi; }
    public void setSinavAdi(String sinavAdi) { this.sinavAdi = sinavAdi; }
    
    public LocalDateTime getSinavTarihi() { return sinavTarihi; }
    public void setSinavTarihi(LocalDateTime sinavTarihi) { this.sinavTarihi = sinavTarihi; }
    
    public String getDerslikAdi() { return derslikAdi; }
    public void setDerslikAdi(String derslikAdi) { this.derslikAdi = derslikAdi; }
    
    public String getGozetmenler() { return gozetmenler; }
    public void setGozetmenler(String gozetmenler) { this.gozetmenler = gozetmenler; }
    
    public List<Ogrenci> getOgrenciler() { return ogrenciler; }
    public void setOgrenciler(List<Ogrenci> ogrenciler) { this.ogrenciler = ogrenciler; }
    
    public Map<String, String> getOturmaPozisyonlari() { return oturmaPozisyonlari; }
    public void setOturmaPozisyonlari(Map<String, String> oturmaPozisyonlari) { 
        this.oturmaPozisyonlari = oturmaPozisyonlari; 
    }
    
    public LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
    public void setOlusturmaTarihi(LocalDateTime olusturmaTarihi) { 
        this.olusturmaTarihi = olusturmaTarihi; 
    }
    
    public LocalDateTime getOnayTarihi() { return onayTarihi; }
    public void setOnayTarihi(LocalDateTime onayTarihi) { this.onayTarihi = onayTarihi; }
    
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    
    public boolean isOnaylandi() { return onaylandi; }
    public void setOnaylandi(boolean onaylandi) { this.onaylandi = onaylandi; }
}
