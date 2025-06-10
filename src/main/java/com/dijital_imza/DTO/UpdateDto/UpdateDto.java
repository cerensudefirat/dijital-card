package com.dijital_imza.DTO.UpdateDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String bio;
    private String job;
}
