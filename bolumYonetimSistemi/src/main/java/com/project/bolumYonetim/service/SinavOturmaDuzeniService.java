package com.project.bolumYonetim.service;

import com.project.bolumYonetim.model.*;
import com.project.bolumYonetim.repository.SinavOturmaDuzeniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.time.LocalDateTime;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SinavOturmaDuzeniService {

    @Autowired
    private SinavOturmaDuzeniRepository sinavOturmaDuzeniRepository;

    @Autowired
    private SinavProgramiService sinavProgramiService;

    @Autowired
    private DerslikService derslikService;

    // Tüm sınav oturma düzenlerini getir (son oluşturulma tarihine göre azalan sıralama)
    public List<SinavOturmaDuzeni> getAllSinavOturmaDuzeni() {
        // Repository'de findAllOrderByOlusturmaTarihiDesc() metodu yoksa bunu yazman gerekir
        return sinavOturmaDuzeniRepository.findAllOrderByOlusturmaTarihiDesc();
    }

    // ID'ye göre oturma düzeni getir
    public Optional<SinavOturmaDuzeni> getById(Long id) {
        return sinavOturmaDuzeniRepository.findById(id);
    }

    // Rastgele oturma düzeni oluştur
    public SinavOturmaDuzeni rastgeleOturmaDuzeniOlustur(
        Derslik derslik,
        String sinavAdi,
        LocalDateTime sinavTarihi, // ← Artık LocalDateTime
        List<Ogrenci> ogrenciler,
        String gozetmenler)
{

        // Çakışma kontrolü
        List<SinavOturmaDuzeni> cakisanSinavlar = sinavOturmaDuzeniRepository
                .findConflictingExams(derslik.getIsim(), sinavTarihi);

        if (!cakisanSinavlar.isEmpty()) {
            throw new IllegalStateException("Bu derslikte aynı tarihte başka bir sınav mevcut!");
        }

        // Derslik kapasitesi kontrolü
        if (ogrenciler.size() > derslik.getKapasite()) {
            throw new IllegalStateException("Öğrenci sayısı (" + ogrenciler.size() +
                    ") derslik kapasitesini (" + derslik.getKapasite() + ") aşıyor!");
        }

        // Öğrenci listesini karıştır
        List<Ogrenci> karisikOgrenciler = new ArrayList<>(ogrenciler);
        Collections.shuffle(karisikOgrenciler);

        // Oturma düzeni oluştur
        SinavOturmaDuzeni oturmaDuzeni = new SinavOturmaDuzeni();
        oturmaDuzeni.setSinavAdi(sinavAdi);
        // Tarih string yerine LocalDateTime olarak set et
        oturmaDuzeni.setSinavTarihi(sinavTarihi);
        oturmaDuzeni.setDerslikAdi(derslik.getIsim());
        oturmaDuzeni.setGozetmenler(gozetmenler);
        oturmaDuzeni.setOgrenciler(karisikOgrenciler);
        oturmaDuzeni.setOlusturmaTarihi(LocalDateTime.now());
        oturmaDuzeni.setDurum("TASLAK");
        oturmaDuzeni.setOnaylandi(false);

        // Oturma pozisyonlarını oluştur
        Map<String, String> oturmaPozisyonlari = oturmaPozisyonlariOlustur(derslik, karisikOgrenciler);
        oturmaDuzeni.setOturmaPozisyonlari(oturmaPozisyonlari);

        return sinavOturmaDuzeniRepository.save(oturmaDuzeni);
    }

public SinavOturmaDuzeni sinavProgramindanOturmaDuzeniOlustur(
        Long sinavProgramiId,
        List<Ogrenci> ogrenciler) {

    Optional<SinavProgrami> sinavProgramiOpt = sinavProgramiService.getById(sinavProgramiId);
    if (sinavProgramiOpt.isEmpty()) {
        throw new IllegalArgumentException("Sınav programı bulunamadı: " + sinavProgramiId);
    }

    SinavProgrami sinavProgrami = sinavProgramiOpt.get();

    String sinavAdi = sinavProgrami.getDers().getDers_adi();

    // LocalDate ve LocalTime'dan LocalDateTime oluştur
    LocalDateTime sinavTarihi = LocalDateTime.of(sinavProgrami.getTarih(), sinavProgrami.getSaat());

    String gozetmenler = "";
    if (sinavProgrami.getGozetmen() != null) {
        gozetmenler = sinavProgrami.getGozetmen().getUnvan() + " " + sinavProgrami.getGozetmen().getIsim() + " " + sinavProgrami.getGozetmen().getSoyisim();
    }

    return rastgeleOturmaDuzeniOlustur(
            sinavProgrami.getDerslik(),
            sinavAdi,
            sinavTarihi,
            ogrenciler,
            gozetmenler);
            
}


    // Oturma pozisyonlarını oluştur
    private Map<String, String> oturmaPozisyonlariOlustur(Derslik derslik, List<Ogrenci> ogrenciler) {
        Map<String, String> pozisyonlar = new HashMap<>();

        Integer satirSayisi = derslik.getGenislik() != null ? derslik.getGenislik() : 10;
        Integer sutunSayisi = derslik.getYukseklik() != null ? derslik.getYukseklik() : 6;

        int ogrenciIndex = 0;

        for (int satir = 1; satir <= satirSayisi && ogrenciIndex < ogrenciler.size(); satir++) {
            for (int sutun = 1; sutun <= sutunSayisi && ogrenciIndex < ogrenciler.size(); sutun++) {
                String pozisyon = satir + "-" + sutun;
                Ogrenci ogrenci = ogrenciler.get(ogrenciIndex);
                pozisyonlar.put(pozisyon, ogrenci.getAd() + " " + ogrenci.getSoyad() +
                        " (" + ogrenci.getOgrenciNo() + ")");
                ogrenciIndex++;
            }
        }

        return pozisyonlar;
    }

    // Oturma düzeni matrisini oluştur
    public String[][] oturmaDuzeniMatrisiOlustur(SinavOturmaDuzeni oturmaDuzeni, Derslik derslik) {
        Integer satirSayisi = derslik.getGenislik() != null ? derslik.getGenislik() : 10;
        Integer sutunSayisi = derslik.getYukseklik() != null ? derslik.getYukseklik() : 6;

        String[][] matris = new String[satirSayisi][sutunSayisi];

        // Matrisi boş yerlerle doldur
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                matris[i][j] = "BOŞ";
            }
        }

        // Oturma pozisyonlarını matrise yerleştir
        Map<String, String> pozisyonlar = oturmaDuzeni.getOturmaPozisyonlari();

        if (pozisyonlar != null) {
            for (Map.Entry<String, String> entry : pozisyonlar.entrySet()) {
                try {
                    String[] koordinat = entry.getKey().split("-");
                    if (koordinat.length == 2) {
                        int satir = Integer.parseInt(koordinat[0]) - 1;
                        int sutun = Integer.parseInt(koordinat[1]) - 1;

                        if (satir >= 0 && satir < satirSayisi && sutun >= 0 && sutun < sutunSayisi) {
                            matris[satir][sutun] = entry.getValue();
                        }
                    }
                } catch (NumberFormatException e) {
                    // Geçersiz koordinat formatı - skip
                }
            }
        }

        return matris;
    }

    // Oturma düzenini onayla
    public void oturmaDuzeniOnayla(Long id) {
        SinavOturmaDuzeni oturmaDuzeni = sinavOturmaDuzeniRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oturma düzeni bulunamadı: " + id));

        oturmaDuzeni.setOnaylandi(true);
        oturmaDuzeni.setDurum("ONAYLANDI");
        oturmaDuzeni.setOnayTarihi(LocalDateTime.now());

        sinavOturmaDuzeniRepository.save(oturmaDuzeni);
    }

    // Oturma düzenini sil
    public void deleteSinavOturmaDuzeni(Long id) {
        if (!sinavOturmaDuzeniRepository.existsById(id)) {
            throw new IllegalArgumentException("Oturma düzeni bulunamadı: " + id);
        }
        sinavOturmaDuzeniRepository.deleteById(id);
    }

    // Filtreleme (derslik adı ve durum)
    public List<SinavOturmaDuzeni> filtrele(String derslikAdi, String durum) {
        return sinavOturmaDuzeniRepository.findByFilters(derslikAdi, durum);
    }

    // Derslik adına göre oturma düzenlerini getir
    public List<SinavOturmaDuzeni> getByDerslikAdi(String derslikAdi) {
        return sinavOturmaDuzeniRepository.findByDerslikAdi(derslikAdi);
    }

    // Duruma göre oturma düzenlerini getir
    public List<SinavOturmaDuzeni> getByDurum(String durum) {
        return sinavOturmaDuzeniRepository.findByDurum(durum);
    }

    // Onaylanmış oturma düzenlerini getir
    public List<SinavOturmaDuzeni> getOnaylananDuzenler() {
        return sinavOturmaDuzeniRepository.findByOnaylandiTrue();
    }

    // Sınav adına göre oturma düzenlerini getir
    public List<SinavOturmaDuzeni> getBySinavAdi(String sinavAdi) {
        return sinavOturmaDuzeniRepository.findBySinavAdi(sinavAdi);
    }

    // Oturma düzenini güncelle
    public SinavOturmaDuzeni updateSinavOturmaDuzeni(Long id, SinavOturmaDuzeni updatedDuzen) {
        SinavOturmaDuzeni existingDuzen = sinavOturmaDuzeniRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oturma düzeni bulunamadı: " + id));

        existingDuzen.setSinavAdi(updatedDuzen.getSinavAdi());
        existingDuzen.setSinavTarihi(updatedDuzen.getSinavTarihi());
        existingDuzen.setGozetmenler(updatedDuzen.getGozetmenler());
        existingDuzen.setDurum(updatedDuzen.getDurum());

        return sinavOturmaDuzeniRepository.save(existingDuzen);
    }

    // Çakışma kontrolü
    public boolean checkConflict(String derslikAdi, LocalDateTime sinavTarihi, Long excludeId) {
        List<SinavOturmaDuzeni> conflicts = sinavOturmaDuzeniRepository
                .findConflictingExams(derslikAdi, sinavTarihi);

        return conflicts.stream().anyMatch(s -> !s.getId().equals(excludeId));
    }

    // Belirli tarihteki tüm sınavları getir (SinavProgrami üzerinden)
    public List<SinavProgrami> getSinavlarByTarih(LocalDate tarih) {
        return sinavProgramiService.getByTarih(tarih);
    }

    // Sınav programından oturma düzeni var mı kontrol et
    public boolean sinavProgramiIcinOturmaDuzeniVarMi(Long sinavProgramiId) {
        Optional<SinavProgrami> sinavProgrami = sinavProgramiService.getById(sinavProgramiId);
        if (sinavProgrami.isEmpty()) return false;

        SinavProgrami sp = sinavProgrami.get();
        String sinavAdi = sp.getDers().getDers_adi() ;
        String sinavTarihi = sp.getTarih().toString() + " " + sp.getSaat().toString();

        List<SinavOturmaDuzeni> mevcutDuzenler = sinavOturmaDuzeniRepository.findBySinavAdi(sinavAdi);
        return mevcutDuzenler.stream()
                .anyMatch(d -> d.getSinavTarihi().equals(sinavTarihi));
    }

    // Günlük sınav oturma düzenlerini getir

    public void pdfRaporOlustur(SinavOturmaDuzeni oturmaDuzeni) throws Exception {
        Derslik derslik = derslikService.getByIsim(oturmaDuzeni.getDerslikAdi())
                .orElseThrow(() -> new RuntimeException("Derslik bulunamadı!"));

        String[][] matris = this.oturmaDuzeniMatrisiOlustur(oturmaDuzeni, derslik);

        String pdfPath = "pdf/sinav_oturma_" + oturmaDuzeni.getId() + ".pdf";
        new File("pdf").mkdirs(); // klasör yoksa oluştur

        PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Sınav Oturma Düzeni Raporu")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        document.add(new Paragraph("Sınav Adı: " + oturmaDuzeni.getSinavAdi()));
        document.add(new Paragraph("Derslik: " + oturmaDuzeni.getDerslikAdi()));
        document.add(new Paragraph("Tarih: " + oturmaDuzeni.getSinavTarihi().toString()));
        document.add(new Paragraph("Gözetmenler: " + oturmaDuzeni.getGozetmenler()));
        document.add(new Paragraph(" ")); // boşluk

        // Matris tablosu
        int rows = matris.length;
        int cols = matris[0].length;

        Table table = new Table(UnitValue.createPercentArray(cols)).useAllAvailableWidth();

        for (String[] row : matris) {
            for (String cell : row) {
                table.addCell(new Cell().add(new Paragraph(cell != null ? cell : ""))
                        .setTextAlignment(TextAlignment.CENTER));
            }
        }

        document.add(table);
        document.close();
    }

}
