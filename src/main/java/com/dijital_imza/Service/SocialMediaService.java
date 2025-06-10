package com.dijital_imza.Service;

import com.dijital_imza.DTO.SocialMediaDto.DtoSocialMediaRequest;
import com.dijital_imza.DTO.SocialMediaDto.DtoSocialMediaResponse;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Entity.SocialMedia;
import com.dijital_imza.repository.SocialMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialMediaService {

    @Autowired
    private SocialMediaRepository linkRepository;

    public void addOrUpdateLink(Kullanici kullanici, DtoSocialMediaRequest dto) {
        SocialMedia link = linkRepository
                .findByKullaniciAndPlatform(kullanici, dto.getPlatform())
                .orElse(new SocialMedia());

        link.setPlatform(dto.getPlatform());
        link.setUrl(dto.getUrl());
        link.setKullanici(kullanici);

        linkRepository.save(link);
    }

    public List<DtoSocialMediaResponse> getAllLinks(Kullanici kullanici) {
        return linkRepository.findByKullanici(kullanici).stream()
                .map(link -> new DtoSocialMediaResponse(link.getPlatform(), link.getUrl()))
                .toList();
    }
}
