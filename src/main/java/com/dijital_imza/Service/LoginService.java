package com.dijital_imza.Service;

import com.dijital_imza.DTO.LoginDto.LoginResponseDto;
import com.dijital_imza.DTO.SigninDto.SigninDto;
import com.dijital_imza.DTO.SigninDto.SigninResponseDto;
import com.dijital_imza.DTO.UpdateDto.UpdateDto;
import com.dijital_imza.Entity.Kullanici;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface LoginService {

    LoginResponseDto  login(SigninDto signinDto);

    SigninResponseDto signin(SigninDto signinDto);

    SigninResponseDto updateUser(UpdateDto dto, HttpServletRequest request);

    SigninResponseDto logout(HttpServletRequest request);

    SigninResponseDto uploadProfilePhoto(MultipartFile file, HttpServletRequest request);

    Kullanici getUserById(Long id);

    Integer   getLoggedInUserId(HttpServletRequest request);

    List<Kullanici> getAllUsers();
}
