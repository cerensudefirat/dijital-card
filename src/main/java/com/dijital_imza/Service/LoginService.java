package com.dijital_imza.Service;

import com.dijital_imza.DTO.LoginDto.LoginResponseDto;
import com.dijital_imza.DTO.SigninDto.SigninDto;
import com.dijital_imza.DTO.SigninDto.SigninResponseDto;
import com.dijital_imza.DTO.UpdateDto.UpdateDto;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.Security.JwtUtil;
import com.dijital_imza.repository.KullaniciRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private JwtUtil jwtUtil;

    public static Integer id;

    public LoginResponseDto login(SigninDto signinDto) {
        Kullanici kullanici= kullaniciRepository.findByEmail(signinDto.getEmail()).orElse(null);
        if (kullanici != null && kullanici.getPassword().equals(signinDto.getPassword())) {
            String token = jwtUtil.generateToken(kullanici.getId());
            return new LoginResponseDto("Login successful!", true, token);
        }
        return new LoginResponseDto("Invalid email or password.", false, null);
    }

    public Kullanici getUserById(Long id) {
        Optional<Kullanici> user = kullaniciRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public SigninResponseDto signin(SigninDto signinDto) {

        if (signinDto.getEmail() == null || signinDto.getEmail().isEmpty()) {
            return new SigninResponseDto("Email cannot be null or empty.", false);
        }

        Kullanici existingUser = kullaniciRepository.findByEmail(signinDto.getEmail()).orElse(null);
        if (existingUser != null) {
            return new SigninResponseDto("User already exists with this email.", false);
        }

        Kullanici newUser = new Kullanici();
        newUser.setName(signinDto.getName());
        newUser.setSurname(signinDto.getSurname());
        newUser.setEmail(signinDto.getEmail());
        newUser.setPassword(signinDto.getPassword());

        kullaniciRepository.save(newUser);

        return new SigninResponseDto("User successfully registered!", true);
    }
    public SigninResponseDto updateUser(UpdateDto dto, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return new SigninResponseDto("Authorization header eksik veya hatalı.", false);
        }
        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return new SigninResponseDto("Geçersiz veya süresi dolmuş token.", false);
        }

        Long userId = jwtUtil.extractUserId(token);
        Optional<Kullanici> opt = kullaniciRepository.findById(userId);
        if (opt.isEmpty()) {
            return new SigninResponseDto("Kullanıcı bulunamadı.", false);
        }
        Kullanici existing = opt.get();
        existing.setName(dto.getName());
        existing.setSurname(dto.getSurname());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        existing.setBio(dto.getBio());
        existing.setJob(dto.getJob());
        kullaniciRepository.save(existing);

        return new SigninResponseDto("Profil güncelleme başarılı!", true);
    }


    public SigninResponseDto logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new SigninResponseDto("Çıkış başarılı.", true);
    }

    public Integer getLoggedInUserId(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        String token = auth.substring(7);
        if (!jwtUtil.validateToken(token)) return null;
        return Integer.valueOf(jwtUtil.getSubject(token));
    }
    public SigninResponseDto uploadProfilePhoto(MultipartFile file, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return new SigninResponseDto("Authorization header eksik veya hatalı.", false);
        }

        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return new SigninResponseDto("Geçersiz veya süresi dolmuş token.", false);
        }

        Long userId = jwtUtil.extractUserId(token);
        Optional<Kullanici> opt = kullaniciRepository.findById(userId);
        if (opt.isEmpty()) {
            return new SigninResponseDto("Kullanıcı bulunamadı.", false);
        }

        Kullanici kullanici = opt.get();

        try {
            String uploadsDir = "uploads/";
            String originalFileName = file.getOriginalFilename();
            String fileName = "user_" + userId + "" + System.currentTimeMillis() + "" + originalFileName;

            File uploadDir = new File(uploadsDir);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Path filePath = Paths.get(uploadsDir + fileName);
            Files.write(filePath, file.getBytes());

            kullanici.setProfilFoto(filePath.toString());
            kullaniciRepository.save(kullanici);

            return new SigninResponseDto("Profil fotoğrafı başarıyla yüklendi.", true);
        } catch (IOException e) {
            return new SigninResponseDto("Dosya yüklenirken hata oluştu.", false);
        }
    }

    public List<Kullanici> getAllUsers() {
        return kullaniciRepository.findAll();
}


}