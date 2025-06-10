package com.dijital_imza.Service;

import com.dijital_imza.DTO.DtoProje;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Entity.Proje;
import com.dijital_imza.Security.JwtUtil;
import com.dijital_imza.repository.KullaniciRepository;
import com.dijital_imza.repository.ProjeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjeService {

    @Autowired
    private ProjeRepository projeRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Token'dan kullanıcı ID'sini al ve doğrula
    private Long getUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header eksik veya geçersiz.");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token geçersiz veya süresi dolmuş.");
        }

        return jwtUtil.extractUserId(token);
    }

    public Proje projeEkle(DtoProje dto, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        Kullanici kullanici = kullaniciRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        Proje proje = new Proje();
        proje.setAdi(dto.getAdi());
        proje.setAciklama(dto.getAciklama());
        proje.setTeknolojiler(dto.getTeknolojiler());
        proje.setProjeLinki(dto.getProjeLinki());
        proje.setGorselUrl(dto.getGorselUrl());
        proje.setKullanici(kullanici);

        return projeRepository.save(proje);
    }

    public List<Proje> kullanicininProjeleri(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return projeRepository.findByKullanici_id(userId);
    }

    public Proje guncelle(Long id, DtoProje dto, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        Proje proje = projeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı."));

        if (!proje.getKullanici().getId().equals(userId)) {
            throw new RuntimeException("Bu projeyi güncelleme yetkiniz yok.");
        }

        proje.setAdi(dto.getAdi());
        proje.setAciklama(dto.getAciklama());
        proje.setTeknolojiler(dto.getTeknolojiler());
        proje.setProjeLinki(dto.getProjeLinki());
        proje.setGorselUrl(dto.getGorselUrl());

        return projeRepository.save(proje);
    }

    public void sil(Long id, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        Proje proje = projeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı."));

        if (!proje.getKullanici().getId().equals(userId)) {
            throw new RuntimeException("Bu projeyi silme yetkiniz yok.");
        }

        projeRepository.delete(proje);
    }
}

