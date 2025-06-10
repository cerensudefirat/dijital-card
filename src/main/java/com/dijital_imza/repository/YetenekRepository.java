package com.dijital_imza.repository;

import com.dijital_imza.Entity.Yetenek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YetenekRepository extends JpaRepository<Yetenek, Long> {
}
