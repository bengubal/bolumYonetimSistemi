package com.project.bolumYonetim.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "notlar")
public class Notlar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ders_programi_id")
    private DersProgrami dersProgrami;

    @ManyToOne
    @JoinColumn(name = "ogretim_uyesi_id")
    private OgretimUyesi ogretimUyesi;

    private String notMetni;
}
