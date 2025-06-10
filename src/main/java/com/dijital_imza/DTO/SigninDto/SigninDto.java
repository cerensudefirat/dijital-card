package com.dijital_imza.DTO.SigninDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninDto {
    private String name;
    private String surname;
    private String email;
    private String password;
}
