package com.dijital_imza.controller;
import com.dijital_imza.DTO.MessageDTO;
import com.dijital_imza.Service.MessageService;
import com.dijital_imza.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * STOMP üzerinden gelen chat mesajını işler.
     * Principal olarak, WebSocketConfig interceptor’ında set edilen userId (subject) gelir.
     */
    @MessageMapping("/chat")
    public void processMessage(
            @Payload MessageDTO messageDTO,
            Principal principal) {

        // principal.getName() token subject olarak atadığımız userId’yi döndürür
        Long senderId = Long.parseLong(principal.getName());
        messageDTO.setSenderId(senderId);

        Message saved = messageService.saveMessage(messageDTO);

        // Alıcıya gerçek zamanlı gönderim
        messagingTemplate.convertAndSendToUser(
                messageDTO.getRecipientId().toString(),
                "/queue/messages",
                saved
        );
    }

    @PostMapping("/message")
    public ResponseEntity<Message> sendViaRest(
            @RequestBody MessageDTO dto,
            Authentication auth) {

        // authentication.getName() token subject (userId) olarak geliyor
        Long senderId = Long.parseLong(auth.getName());
        dto.setSenderId(senderId);

        Message saved = messageService.saveMessage(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * GET /api/conversation?otherUserId={id}
     * REST üzerinden geçmiş sohbeti döner.
     */
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getHistory(
            @RequestParam Long otherUserId,
            Authentication authentication) {

        // Authentication.getName() yine token subject (userId) olarak gelir
        Long userId = Long.parseLong(authentication.getName());

        List<Message> conv = messageService.getConversation(userId, otherUserId);
        return ResponseEntity.ok(conv);
    }
}
