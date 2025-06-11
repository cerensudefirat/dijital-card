package com.dijital_imza.Service;

import com.dijital_imza.DTO.DtoYetenek;
import com.dijital_imza.DTO.MessageDTO;
import com.dijital_imza.Entity.Message;
import com.dijital_imza.Entity.Yetenek;

import java.util.List;

public interface MessageService {

    Message saveMessage(MessageDTO dto);

    List<Message> getConversation(Long userId, Long otherUserId);

    interface YetenekService {

        List<Yetenek> getYetenekList(Long kullaniciId);

        String addYetenek(Long kullaniciId, DtoYetenek dtoYetenek);

        String removeYetenek(Long kullaniciId, Long yetenekId);
    }
}
