package com.dijital_imza.controller;

import com.dijital_imza.DTO.LoginDto.LoginResponseDto;
import com.dijital_imza.DTO.SigninDto.SigninDto;
import com.dijital_imza.DTO.SigninDto.SigninResponseDto;
import com.dijital_imza.DTO.UpdateDto.UpdateDto;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody SigninDto signinDto) {
        LoginResponseDto response = loginService.login(signinDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/{id}")
    public Kullanici getUserById(@PathVariable(name = "id") Long id) {
        return loginService.getUserById(id);
    }

    @PostMapping("/signup")
    public ResponseEntity<SigninResponseDto> signin(@RequestBody SigninDto signinDto) {
        SigninResponseDto response = loginService.signin(signinDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<SigninResponseDto> updateUser(
            @RequestBody UpdateDto updateUserDto,
            HttpServletRequest request
    ) {
        SigninResponseDto response = loginService.updateUser(updateUserDto, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<SigninResponseDto> logout(HttpServletRequest request) {
        SigninResponseDto response = loginService.logout(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-user-id")
    public ResponseEntity<Integer> getLoggedInUserId(HttpServletRequest request) {
        Integer userId = loginService.getLoggedInUserId(request);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PutMapping("/upload-photo")
    public ResponseEntity<SigninResponseDto> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {

        SigninResponseDto response = loginService.uploadProfilePhoto(file, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Kullanici>> getAllUsers() {
        List<Kullanici> users = loginService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
