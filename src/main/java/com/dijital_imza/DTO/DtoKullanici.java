package com.dijital_imza.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoKullanici {
    private Long kullanici_id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String bio;
    private String job;
}
