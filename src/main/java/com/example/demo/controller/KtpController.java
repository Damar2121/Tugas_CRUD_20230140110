package com.example.demo.controller;

import com.example.demo.dto.KtpDto;
import com.example.demo.service.KtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ktp")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend access
public class KtpController {

    private final KtpService ktpService;

    @PostMapping
    public ResponseEntity<KtpDto> createKtp(@Valid @RequestBody KtpDto ktpDto) {
        return new ResponseEntity<>(ktpService.createKtp(ktpDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<KtpDto>> getAllKtp() {
        return ResponseEntity.ok(ktpService.getAllKtp());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KtpDto> getKtpById(@PathVariable Long id) {
        return ResponseEntity.ok(ktpService.getKtpById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KtpDto> updateKtp(@PathVariable Long id, @Valid @RequestBody KtpDto ktpDto) {
        return ResponseEntity.ok(ktpService.updateKtp(id, ktpDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKtp(@PathVariable Long id) {
        ktpService.deleteKtp(id);
        return ResponseEntity.noContent().build();
    }
}
