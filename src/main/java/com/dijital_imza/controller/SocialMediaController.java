package com.dijital_imza.controller;

import com.dijital_imza.DTO.SocialMediaDto.DtoSocialMediaRequest;
import com.dijital_imza.DTO.SocialMediaDto.DtoSocialMediaResponse;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Service.SocialMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sosyal-medya")
@RequiredArgsConstructor
public class SocialMediaController {

    @Autowired
    private SocialMediaService service;

    @PostMapping
    public ResponseEntity<?> addOrUpdateLink(@AuthenticationPrincipal Kullanici kullanici,
                                             @RequestBody DtoSocialMediaRequest dto) {
        service.addOrUpdateLink(kullanici, dto);
        return ResponseEntity.ok("Link başarıyla kaydedildi.");
    }

    @GetMapping
    public ResponseEntity<List<DtoSocialMediaResponse>> getLinks(@AuthenticationPrincipal Kullanici kullanici) {
        return ResponseEntity.ok(service.getAllLinks(kullanici));
    }
}
