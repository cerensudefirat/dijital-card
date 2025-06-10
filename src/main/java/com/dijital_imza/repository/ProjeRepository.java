package com.dijital_imza.repository;

import com.dijital_imza.Entity.Proje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjeRepository extends JpaRepository<Proje, Long> {
    List<Proje> findByKullanici_id(Long kullaniciId);
}

