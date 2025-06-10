package com.dijital_imza.DTO.SigninDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponseDto {
    private String message;
    private boolean success;
}
