package com.dijital_imza.Service.impl;

import com.dijital_imza.DTO.DtoProje;
import com.dijital_imza.DTO.DtoYetenek;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Entity.Proje;
import com.dijital_imza.Entity.Yetenek;
import com.dijital_imza.Security.JwtUtil;
import com.dijital_imza.Service.MessageService;
import com.dijital_imza.Service.ProjeService;
import com.dijital_imza.repository.KullaniciRepository;
import com.dijital_imza.repository.ProjeRepository;
import com.dijital_imza.repository.YetenekRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjeServiceImpl implements ProjeService {

    @Autowired
    private ProjeRepository projeRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private JwtUtil jwtUtil;

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

    @Override
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

    @Override
    public List<Proje> kullanicininProjeleri(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return projeRepository.findByKullanici_id(userId);
    }

    @Override
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

    @Override
    public void sil(Long id, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        Proje proje = projeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı."));

        if (!proje.getKullanici().getId().equals(userId)) {
            throw new RuntimeException("Bu projeyi silme yetkiniz yok.");
        }

        projeRepository.delete(proje);
    }

    @Service
    public static class YetenekServiceImpl implements MessageService.YetenekService {

        @Autowired
        private YetenekRepository yetenekRepository;
        @Autowired
        private KullaniciRepository kullaniciRepository;

        public YetenekServiceImpl(YetenekRepository yetenekRepository,
                                  KullaniciRepository kullaniciRepository) {
            this.yetenekRepository = yetenekRepository;
            this.kullaniciRepository = kullaniciRepository;
        }

        @Override
        public List<Yetenek> getYetenekList(Long kullaniciId) {
            Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
            return kullanici.getYetenek();
        }

        @Override
        @Transactional
        public String addYetenek(Long kullaniciId, DtoYetenek dtoYetenek) {
            Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
            Yetenek yetenek = new Yetenek();
            yetenek.setAciklama(dtoYetenek.getAciklama());
            yetenek = yetenekRepository.save(yetenek);
            kullanici.getYetenek().add(yetenek);
            yetenek.getKullanici().add(kullanici);
            kullaniciRepository.save(kullanici);
            yetenekRepository.save(yetenek);

            return "Yetenek başarıyla eklendi.";
        }

        @Override
        @Transactional
        public String removeYetenek(Long kullaniciId, Long yetenekId) {
            Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
            Yetenek yetenek = yetenekRepository.findById(yetenekId)
                    .orElseThrow(() -> new RuntimeException("Yetenek bulunamadı"));
            kullanici.getYetenek().remove(yetenek);
            yetenek.getKullanici().remove(kullanici);
            kullaniciRepository.save(kullanici);
            yetenekRepository.save(yetenek);
            return "Yetenek başarıyla silindi.";
        }
    }
}
