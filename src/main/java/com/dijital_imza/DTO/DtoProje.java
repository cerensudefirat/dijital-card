package com.dijital_imza.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoProje {
    private String adi;
    private String aciklama;
    private String teknolojiler;
    private String projeLinki;
    private String gorselUrl;
}
