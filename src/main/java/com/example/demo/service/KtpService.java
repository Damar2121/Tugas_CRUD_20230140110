package com.example.demo.service;

import com.example.demo.dto.KtpDto;
import java.util.List;

public interface KtpService {
    KtpDto createKtp(KtpDto ktpDto);
    List<KtpDto> getAllKtp();
    KtpDto getKtpById(Long id);
    KtpDto updateKtp(Long id, KtpDto ktpDto);
    void deleteKtp(Long id);
}
