package com.dijital_imza.DTO.SocialMediaDto;

import com.dijital_imza.Entity.SocialPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoSocialMediaResponse {
    private SocialPlatform platform;
    private String url;
}
