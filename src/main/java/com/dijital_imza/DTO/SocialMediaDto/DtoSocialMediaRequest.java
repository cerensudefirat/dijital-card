package com.dijital_imza.DTO.SocialMediaDto;

import com.dijital_imza.Entity.SocialPlatform;
import lombok.Data;

@Data
public class DtoSocialMediaRequest {
    private SocialPlatform platform;
    private String url;
}
