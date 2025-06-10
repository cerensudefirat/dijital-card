package com.dijital_imza.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yetenek")
public class Yetenek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yetenek_id;
    @Column
    private String aciklama;

    @ManyToMany
    @JoinTable(
            name = "kullanici_yetenek",
            joinColumns =@JoinColumn(name = "yetenek_id"),
            inverseJoinColumns = @JoinColumn(name = "kullanici_id")
    )
    @JsonBackReference
    private List<Kullanici> kullanici=new ArrayList<>();
}
