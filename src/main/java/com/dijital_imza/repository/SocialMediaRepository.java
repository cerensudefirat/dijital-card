package com.dijital_imza.repository;

import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Entity.SocialMedia;
import com.dijital_imza.Entity.SocialPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
    List<SocialMedia> findByKullanici(Kullanici kullanici);
    Optional<SocialMedia> findByKullaniciAndPlatform(Kullanici kullanici, SocialPlatform platform);
}
