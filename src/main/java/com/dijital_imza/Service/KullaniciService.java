package com.dijital_imza.Service;

import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.repository.KullaniciRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KullaniciService {
    private final KullaniciRepository repo;

    public KullaniciService(KullaniciRepository repo) {
        this.repo = repo;
    }

    public List<Kullanici> araKullanici(String keyword) {
        return repo.searchByKeyword(keyword);
    }
}
