package com.myfolio.dto.linkdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkResponseDTO {

    private Long id;
    private String username;
    private String title;
    private String urlType;
    private String iconUrl;
    private String url;
    private Boolean isVisible;
}
