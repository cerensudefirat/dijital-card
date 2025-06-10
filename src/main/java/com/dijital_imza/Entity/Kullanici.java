package com.dijital_imza.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Kullanici")
public class Kullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String bio;
    @Column
    private String job;
    @Column
    private String profilFoto;

    @ManyToMany(mappedBy ="kullanici")
    @JsonManagedReference
    private List<Yetenek> yetenek;
}
