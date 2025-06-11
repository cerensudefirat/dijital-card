package com.dijital_imza.controller;

import com.dijital_imza.DTO.MessageDTO;
import com.dijital_imza.Entity.Message;
import com.dijital_imza.Service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload MessageDTO messageDTO,
            Principal principal) {

        Long senderId = Long.parseLong(principal.getName());
        messageDTO.setSenderId(senderId);

        Message saved = messageService.saveMessage(messageDTO);

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

        Long senderId = Long.parseLong(auth.getName());
        dto.setSenderId(senderId);

        Message saved = messageService.saveMessage(dto);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getHistory(
            @RequestParam Long otherUserId,
            Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());

        List<Message> conv = messageService.getConversation(userId, otherUserId);
        return ResponseEntity.ok(conv);
    }
}