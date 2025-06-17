package com.project.bolumYonetim.controller;

import com.project.bolumYonetim.model.*;
import com.project.bolumYonetim.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/sinav-oturma")
public class SinavOturmaDuzeniController {

    @Autowired
    private SinavOturmaDuzeniService sinavOturmaDuzeniService;
    
    @Autowired
    private DerslikService derslikService;
    
    @Autowired
    private OgrenciService ogrenciService;
    
    @Autowired
    private SinavProgramiService sinavProgramiService;

    // Tüm sınav oturma düzenlerini listele
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("sinavOturmaDuzenleri", sinavOturmaDuzeniService.getAllSinavOturmaDuzeni());
        return "sinavprogrami/sinav-oturma-list";
    }

    // Yeni sınav oturma düzeni formu
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("sinavOturmaDuzeni", new SinavOturmaDuzeni());
        model.addAttribute("derslikler", derslikService.getAllDerslik());
        model.addAttribute("ogrenciler", ogrenciService.getAllOgrenci());
        // Bugünkü ve gelecekteki sınavları getir
        model.addAttribute("sinavProgramlari", sinavProgramiService.getAll());
        return "sinavprogrami/sinav-oturma-form";
    }

    // Sınav programından oturma düzeni oluştur
    @PostMapping("/sinav-programindan-olustur")
    public String sinavProgramindanOlustur(
            @RequestParam("sinavProgramiId") Long sinavProgramiId,
            @RequestParam("ogrenciIds") List<Long> ogrenciIds,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Sınav programı kontrolü
            if (sinavOturmaDuzeniService.sinavProgramiIcinOturmaDuzeniVarMi(sinavProgramiId)) {
                redirectAttributes.addFlashAttribute("error", 
                    "Bu sınav için zaten bir oturma düzeni mevcut!");
                return "redirect:/sinav-oturma/new";
            }
            
            List<Ogrenci> secilenOgrenciler = ogrenciService.getByIds(ogrenciIds);
            
            SinavOturmaDuzeni oturmaDuzeni = sinavOturmaDuzeniService
                    .sinavProgramindanOturmaDuzeniOlustur(sinavProgramiId, secilenOgrenciler);
            
            redirectAttributes.addFlashAttribute("success", 
                "Sınav oturma düzeni başarıyla oluşturuldu!");
            
            return "redirect:/sinav-oturma/" + oturmaDuzeni.getId();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Oturma düzeni oluşturulurken hata: " + e.getMessage());
            return "redirect:/sinav-oturma/new";
        }
    }

    // Rastgele oturma düzeni oluştur (eski metod)
    @PostMapping("/olustur")
    public String rastgeleOturmaDuzeniOlustur(
            @RequestParam("derslikId") Long derslikId,
            @RequestParam("sinavAdi") String sinavAdi,
            @RequestParam("sinavTarihi") LocalDateTime sinavTarihi,
            @RequestParam("ogrenciIds") List<Long> ogrenciIds,
            @RequestParam("gozetmenler") String gozetmenler,
            RedirectAttributes redirectAttributes) {
        
        try {
            Derslik derslik = derslikService.getById(derslikId)
                    .orElseThrow(() -> new IllegalArgumentException("Derslik bulunamadı"));
            
            List<Ogrenci> secilenOgrenciler = ogrenciService.getByIds(ogrenciIds);
            
            if (secilenOgrenciler.size() > derslik.getKapasite()) {
                redirectAttributes.addFlashAttribute("error", 
                    "Seçilen öğrenci sayısı (" + secilenOgrenciler.size() + 
                    ") derslik kapasitesini (" + derslik.getKapasite() + ") aşıyor!");
                return "redirect:/sinav-oturma/new";
            }
            
            SinavOturmaDuzeni oturmaDuzeni = sinavOturmaDuzeniService.rastgeleOturmaDuzeniOlustur(
                    derslik, sinavAdi, sinavTarihi, secilenOgrenciler, gozetmenler);
            
            redirectAttributes.addFlashAttribute("success", 
                "Sınav oturma düzeni başarıyla oluşturuldu!");
            
            return "redirect:/sinav-oturma/" + oturmaDuzeni.getId();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Oturma düzeni oluşturulurken hata: " + e.getMessage());
            return "redirect:/sinav-oturma/new";
        }
    }

    // Oturma düzeni detaylarını görüntüle
    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        SinavOturmaDuzeni oturmaDuzeni = sinavOturmaDuzeniService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oturma düzeni bulunamadı: " + id));

        model.addAttribute("oturmaDuzeni", oturmaDuzeni);

        Derslik derslik = derslikService.getByIsim(oturmaDuzeni.getDerslikAdi())
                                .orElse(null);
            model.addAttribute("derslik", derslik);

        if (derslik != null) {
            model.addAttribute("derslik", derslik);
        } else {
            model.addAttribute("derslik", new Derslik()); // boş ama null olmayan nesne
        }

        return "sinavprogrami/sinav-oturma-detay";
    }


    // Oturma düzenini matris olarak görüntüle
    @GetMapping("/{id}/matris")
    public String showMatris(@PathVariable Long id, Model model) {
        SinavOturmaDuzeni oturmaDuzeni = sinavOturmaDuzeniService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oturma düzeni bulunamadı: " + id));
        
        Derslik derslik = derslikService.getByIsim(oturmaDuzeni.getDerslikAdi())
                     .orElseThrow(() -> new RuntimeException("Derslik bulunamadı!"));
        String[][] matris = sinavOturmaDuzeniService.oturmaDuzeniMatrisiOlustur(oturmaDuzeni, derslik);
        
        model.addAttribute("oturmaDuzeni", oturmaDuzeni);
        model.addAttribute("derslik", derslik);
        model.addAttribute("matris", matris);
        
        return "sinavprogrami/sinav-oturma-matris";
    }

    // PDF rapor oluştur
    @GetMapping("/{id}/pdf")
    ResponseEntity<byte[]> generatePdfReport (@PathVariable Long id) {
    try {
        SinavOturmaDuzeni oturmaDuzeni = sinavOturmaDuzeniService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oturma düzeni bulunamadı: " + id));

        byte[] pdfBytes = sinavOturmaDuzeniService.pdfRaporOlusturBytes(oturmaDuzeni);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "sinav_oturma_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    // Oturma düzenini onayla
    @PostMapping("/{id}/onayla")
    public String onaylaOturmaDuzeni(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sinavOturmaDuzeniService.oturmaDuzeniOnayla(id);
            redirectAttributes.addFlashAttribute("success", "Oturma düzeni onaylandı!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Onaylama işlemi başarısız: " + e.getMessage());
        }
        
        return "redirect:/sinav-oturma/" + id;
    }

    // Oturma düzenini sil
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sinavOturmaDuzeniService.deleteSinavOturmaDuzeni(id);
            redirectAttributes.addFlashAttribute("success", "Oturma düzeni silindi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Silme işlemi başarısız: " + e.getMessage());
        }
        
        return "redirect:/sinav-oturma";
    }

    // Derslik kapasitesini AJAX ile getir
    @GetMapping("/api/derslik/{id}/kapasite")
    @ResponseBody
    public int getDerslikKapasitesi(@PathVariable Long id) {
        return derslikService.getById(id)
                .map(Derslik::getKapasite)
                .orElse(0);
    }

    // Sınav programı bilgilerini AJAX ile getir
    @GetMapping("/api/sinav-programi/{id}")
    @ResponseBody
    public SinavProgrami getSinavProgramiBilgileri(@PathVariable Long id) {
        return sinavProgramiService.getById(id).orElse(null);
    }

    // Filtreleme
    @GetMapping("/filtre")
    public String filtrele(
            @RequestParam(required = false) String derslikAdi,
            @RequestParam(required = false) String durum,
            Model model) {
        
        List<SinavOturmaDuzeni> filtrelenmisListe = sinavOturmaDuzeniService.filtrele(derslikAdi, durum);
        
        model.addAttribute("sinavOturmaDuzenleri", filtrelenmisListe);
        model.addAttribute("derslikler", derslikService.getAllDerslik());
        model.addAttribute("secilenDerslik", derslikAdi);
        model.addAttribute("secilenDurum", durum);
        
        return "sinavprogrami/sinav-oturma-list";
    }

    // Günlük sınav oturma düzenlerini göster
    @GetMapping("/gunluk")
    public String gunlukOturmaDuzenleri(
            @RequestParam(required = false) String tarih,
            Model model) {
        
        LocalDate seciliTarih = (tarih != null) ? LocalDate.parse(tarih) : LocalDate.now();
        
        List<SinavProgrami> gunlukSinavlar = sinavProgramiService.getByTarih(seciliTarih);
        
        model.addAttribute("sinavProgramlari", gunlukSinavlar);
        model.addAttribute("seciliTarih", seciliTarih);
        
        return "sinavprogrami/gunluk-oturma-duzen";
    }
}