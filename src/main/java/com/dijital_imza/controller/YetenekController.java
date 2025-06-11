package com.dijital_imza.controller;

import com.dijital_imza.DTO.DtoYetenek;
import com.dijital_imza.Entity.Yetenek;
import com.dijital_imza.Security.JwtUtil;
import com.dijital_imza.Service.YetenekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yetenekler")
public class YetenekController {

    @Autowired
    private YetenekService yetenekService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/listele")
    public List<Yetenek> getYetenekler(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtUtil.extractUserId(jwtToken);

        return yetenekService.getYetenekList(userId);
    }

    @PostMapping("/ekle")
    public String addYetenekToUser(@RequestHeader("Authorization") String token, @RequestBody DtoYetenek dtoYetenek) {
        String jwtToken = token.substring(7);
        Long userId = jwtUtil.extractUserId(jwtToken);

        return yetenekService.addYetenek(userId, dtoYetenek);
    }

    @DeleteMapping("/sil/{yetenekId}")
    public String removeYetenekFromUser(@RequestHeader("Authorization") String token, @PathVariable Long yetenekId) {
        String jwtToken = token.substring(7);
        Long userId = jwtUtil.extractUserId(jwtToken);
        return yetenekService.removeYetenek(userId, yetenekId);
    }

}
