package com.dijital_imza.Service;

import com.dijital_imza.DTO.MessageDTO;
import com.dijital_imza.Entity.Kullanici;
import com.dijital_imza.entity.Message;
import com.dijital_imza.repository.KullaniciRepository;
import com.dijital_imza.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final KullaniciRepository kullaniciRepository;

    @Transactional
    public Message saveMessage(MessageDTO dto) {
        Kullanici sender = kullaniciRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Gönderen bulunamadı"));
        Kullanici recipient = kullaniciRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("Alıcı bulunamadı"));

        Message m = new Message();
        m.setSender(sender);
        m.setRecipient(recipient);
        m.setContent(dto.getContent());
        return messageRepository.save(m);
    }

    @Transactional(readOnly = true)
    public List<Message> getConversation(Long userId, Long otherUserId) {
        List<Message> sent = messageRepository.findBySenderIdAndRecipientId(userId, otherUserId);
        List<Message> received = messageRepository.findBySenderIdAndRecipientId(otherUserId, userId);
        sent.addAll(received);
        // isteğe bağlı: timestamp’a göre sırala
        sent.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));
        return sent;
    }
}
