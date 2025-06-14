package com.project.bolumYonetim.model;

import lombok.Data;

@Data
public class OgretimUyesiRequestDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    
    private String isim;
    private String soyisim;
    private String email;
    private String unvan;
}
