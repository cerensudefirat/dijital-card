package com.dijital_imza.Service;

import com.dijital_imza.DTO.DtoYetenek;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Entity.Yetenek;
import com.dijital_imza.repository.KullaniciRepository;
import com.dijital_imza.repository.YetenekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YetenekService {

    @Autowired
    private YetenekRepository yetenekRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    public List<Yetenek> getYetenekList(Long kullaniciId) {
        Kullanici kullanici=kullaniciRepository.findById(kullaniciId).orElse(null);
        if (kullanici==null){
            throw new RuntimeException("Kullanici id not found");
        }
        return kullanici.getYetenek();
    }

    public String addYetenek(Long kullaniciId, DtoYetenek dtoYetenek) {
        Kullanici kullanici=kullaniciRepository.findById(kullaniciId).orElse(null);
        if (kullanici==null){
            return "Kullanici not found";
        }
        Yetenek yetenek=new Yetenek();
        yetenek.setAciklama(dtoYetenek.getAciklama());
        yetenek=yetenekRepository.save(yetenek);
        kullanici.getYetenek().add(yetenek);
        yetenek.getKullanici().add(kullanici);
        kullaniciRepository.save(kullanici);
        yetenekRepository.save(yetenek);
        return "Yetenek başarıyla eklendi.";
    }
    public String removeYetenek(Long kullaniciId, Long yetenekId) {
        Kullanici kullanici=kullaniciRepository.findById(kullaniciId).orElse(null);
        if (kullanici==null){
            return "Kullanici not found";
        }
        Yetenek yetenek=yetenekRepository.findById(yetenekId).orElse(null);
        if (yetenek==null){
            return "Yetenek not found";
        }
        kullanici.getYetenek().remove(yetenek);
        yetenek.getKullanici().remove(kullanici);
        kullaniciRepository.save(kullanici);
        yetenekRepository.save(yetenek);
        return "Yetenek başarıyla silindi";
        }
}