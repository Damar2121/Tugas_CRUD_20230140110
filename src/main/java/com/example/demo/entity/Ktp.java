package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ktp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ktp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomorKtp", unique = true, nullable = false)
    private String nomorKtp;

    @Column(name = "namaLengkap", nullable = false)
    private String namaLengkap;

    @Column(name = "alamat", nullable = false)
    private String alamat;

    @Column(name = "tanggalLahir", nullable = false)
    private LocalDate tanggalLahir;

    @Column(name = "jenisKelamin", nullable = false)
    private String jenisKelamin;
}
