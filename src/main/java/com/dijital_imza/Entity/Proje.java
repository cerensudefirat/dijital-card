package com.dijital_imza.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proje")
public class Proje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String adi;
    @Column
    private String aciklama;
    @Column
    private String teknolojiler;
    @Column
    private String projeLinki;
    @Column
    private String gorselUrl;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    @JsonBackReference
    private Kullanici kullanici;
}

