package com.example.demo.mapper;

import com.example.demo.dto.KtpDto;
import com.example.demo.entity.Ktp;
import org.springframework.stereotype.Component;

@Component
public class KtpMapper {

    public Ktp toEntity(KtpDto dto) {
        if (dto == null) return null;
        return Ktp.builder()
                .id(dto.getId())
                .nomorKtp(dto.getNomorKtp())
                .namaLengkap(dto.getNamaLengkap())
                .alamat(dto.getAlamat())
                .tanggalLahir(dto.getTanggalLahir())
                .jenisKelamin(dto.getJenisKelamin())
                .build();
    }

    public KtpDto toDto(Ktp entity) {
        if (entity == null) return null;
        return KtpDto.builder()
                .id(entity.getId())
                .nomorKtp(entity.getNomorKtp())
                .namaLengkap(entity.getNamaLengkap())
                .alamat(entity.getAlamat())
                .tanggalLahir(entity.getTanggalLahir())
                .jenisKelamin(entity.getJenisKelamin())
                .build();
    }
}
