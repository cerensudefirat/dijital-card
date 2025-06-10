package com.dijital_imza.controller;

import com.dijital_imza.DTO.DtoProje;
import com.dijital_imza.Entity.Proje;
import com.dijital_imza.Service.ProjeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projeler")
public class ProjeController {

    @Autowired
    private ProjeService projeService;

    @PostMapping("/ekle")
    public ResponseEntity<Proje> ekle(@RequestBody DtoProje dto, HttpServletRequest request) {
        return ResponseEntity.ok(projeService.projeEkle(dto, request));
    }

    @GetMapping("/listele")
    public ResponseEntity<List<Proje>> listele(HttpServletRequest request) {
        return ResponseEntity.ok(projeService.kullanicininProjeleri(request));
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<Proje> guncelle(@PathVariable Long id, @RequestBody DtoProje dto, HttpServletRequest request) {
        return ResponseEntity.ok(projeService.guncelle(id, dto, request));
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<Void> sil(@PathVariable Long id, HttpServletRequest request) {
        projeService.sil(id, request);
        return ResponseEntity.ok().build();
    }
}

