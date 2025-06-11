package com.dijital_imza.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long senderId;
    private Long recipientId;
    private String text;
}
