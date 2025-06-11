package com.dijital_imza.Service;

import com.dijital_imza.DTO.DtoProje;
import com.dijital_imza.Entity.Proje;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProjeService {

    Proje projeEkle(DtoProje dto, HttpServletRequest request);

    List<Proje> kullanicininProjeleri(HttpServletRequest request);

    Proje guncelle(Long id, DtoProje dto, HttpServletRequest request);

    void  sil(Long id, HttpServletRequest request);
}
