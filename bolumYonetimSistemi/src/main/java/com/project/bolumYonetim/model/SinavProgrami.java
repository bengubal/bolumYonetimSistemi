package com.project.bolumYonetim.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SinavProgrami {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDate tarih;

        private LocalTime saat;

        @ManyToOne
        @JoinColumn(name = "ders_id")
        private Ders ders;

        @ManyToOne
        @JoinColumn(name = "gozetmen_id")
        private OgretimUyesi gozetmen;

        @ManyToOne
        @JoinColumn(name = "derslik_id")
        private Derslik derslik;
    
}
