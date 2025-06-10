package com.dijital_imza.repository;

import com.dijital_imza.Entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {
    Optional<Kullanici> findByEmail(String email);
}
