package com.dijital_imza.controller;

import com.dijital_imza.Entity.Kullanici;

import com.dijital_imza.Service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KullaniciController {

    @Autowired
    private KullaniciService service;

    public KullaniciController(KullaniciService service) {
        this.service = service;
    }

    @GetMapping("/api/kartlar/ara")
    public List<Kullanici> filtrele(
            @RequestParam("q") String keyword
    ) {
        return service.araKullanici(keyword);
    }
}
