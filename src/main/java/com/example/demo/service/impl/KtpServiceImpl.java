package com.example.demo.service.impl;

import com.example.demo.dto.KtpDto;
import com.example.demo.entity.Ktp;
import com.example.demo.mapper.KtpMapper;
import com.example.demo.repository.KtpRepository;
import com.example.demo.service.KtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KtpServiceImpl implements KtpService {

    private final KtpRepository ktpRepository;
    private final KtpMapper ktpMapper;

    @Override
    public KtpDto createKtp(KtpDto ktpDto) {
        if (ktpRepository.existsByNomorKtp(ktpDto.getNomorKtp())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nomor KTP sudah terdaftar");
        }
        Ktp ktp = ktpMapper.toEntity(ktpDto);
        return ktpMapper.toDto(ktpRepository.save(ktp));
    }

    @Override
    public List<KtpDto> getAllKtp() {
        return ktpRepository.findAll().stream()
                .map(ktpMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public KtpDto getKtpById(Long id) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data KTP tidak ditemukan"));
        return ktpMapper.toDto(ktp);
    }

    @Override
    public KtpDto updateKtp(Long id, KtpDto ktpDto) {
        Ktp existingKtp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data KTP tidak ditemukan"));

        // Check if nomorKtp is being changed and if it already exists
        if (!existingKtp.getNomorKtp().equals(ktpDto.getNomorKtp()) && 
            ktpRepository.existsByNomorKtp(ktpDto.getNomorKtp())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nomor KTP sudah terdaftar");
        }

        existingKtp.setNomorKtp(ktpDto.getNomorKtp());
        existingKtp.setNamaLengkap(ktpDto.getNamaLengkap());
        existingKtp.setAlamat(ktpDto.getAlamat());
        existingKtp.setTanggalLahir(ktpDto.getTanggalLahir());
        existingKtp.setJenisKelamin(ktpDto.getJenisKelamin());

        return ktpMapper.toDto(ktpRepository.save(existingKtp));
    }

    @Override
    public void deleteKtp(Long id) {
        if (!ktpRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data KTP tidak ditemukan");
        }
        ktpRepository.deleteById(id);
    }
}
