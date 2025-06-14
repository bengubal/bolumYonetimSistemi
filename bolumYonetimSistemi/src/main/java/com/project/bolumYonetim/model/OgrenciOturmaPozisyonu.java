package com.project.bolumYonetim.model;


public class OgrenciOturmaPozisyonu {
    private Long id;
    private String ogrenciNo;
    private String ogrenciAdi;
    private String ogrenciSoyadi;
    private int siraNo;
    private String siraKodu; // A1, B2, C3 gibi
    private int satir;
    private int sutun;
    private boolean mevcutMu;
    
    // Constructors
    public OgrenciOturmaPozisyonu() {
        this.mevcutMu = true;
    }
    
    public OgrenciOturmaPozisyonu(String ogrenciNo, String ogrenciAdi, String ogrenciSoyadi, 
                                int siraNo, String siraKodu, int satir, int sutun) {
        this();
        this.ogrenciNo = ogrenciNo;
        this.ogrenciAdi = ogrenciAdi;
        this.ogrenciSoyadi = ogrenciSoyadi;
        this.siraNo = siraNo;
        this.siraKodu = siraKodu;
        this.satir = satir;
        this.sutun = sutun;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getOgrenciNo() { return ogrenciNo; }
    public void setOgrenciNo(String ogrenciNo) { this.ogrenciNo = ogrenciNo; }
    
    public String getOgrenciAdi() { return ogrenciAdi; }
    public void setOgrenciAdi(String ogrenciAdi) { this.ogrenciAdi = ogrenciAdi; }
    
    public String getOgrenciSoyadi() { return ogrenciSoyadi; }
    public void setOgrenciSoyadi(String ogrenciSoyadi) { this.ogrenciSoyadi = ogrenciSoyadi; }
    
    public int getSiraNo() { return siraNo; }
    public void setSiraNo(int siraNo) { this.siraNo = siraNo; }
    
    public String getSiraKodu() { return siraKodu; }
    public void setSiraKodu(String siraKodu) { this.siraKodu = siraKodu; }
    
    public int getSatir() { return satir; }
    public void setSatir(int satir) { this.satir = satir; }
    
    public int getSutun() { return sutun; }
    public void setSutun(int sutun) { this.sutun = sutun; }
    
    public boolean isMevcutMu() { return mevcutMu; }
    public void setMevcutMu(boolean mevcutMu) { this.mevcutMu = mevcutMu; }
    
    // Helper methods
    public String getTamAd() {
        return ogrenciAdi + " " + ogrenciSoyadi;
    }
    
    public String getPozisyonBilgisi() {
        return "Sıra: " + siraKodu + " (Satır: " + satir + ", Sütun: " + sutun + ")";
    }
    
    @Override
    public String toString() {
        return "OgrenciOturmaPozisyonu{" +
                "ogrenciNo='" + ogrenciNo + '\'' +
                ", tamAd='" + getTamAd() + '\'' +
                ", siraKodu='" + siraKodu + '\'' +
                ", pozisyon=(" + satir + "," + sutun + ")" +
                ", mevcutMu=" + mevcutMu +
                '}';
    }
}