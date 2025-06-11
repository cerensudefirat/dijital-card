package com.dijital_imza.repository;

import com.dijital_imza.Entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    @Query("""
        SELECT DISTINCT k
          FROM Kullanici k
     LEFT JOIN k.yetenek y
     LEFT JOIN k.projeler p
     LEFT JOIN k.socialMedias s
         WHERE LOWER(k.name)        LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(k.surname)     LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(k.bio)         LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(k.job)         LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(y.aciklama)    LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(p.adi)         LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(p.aciklama)    LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(p.teknolojiler)LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(s.url)         LIKE LOWER(CONCAT('%', :kw, '%'))
        """)
    List<Kullanici> searchByKeyword(@Param("kw") String keyword);
    Optional<Kullanici> findByEmail(String email);

}
