package com.myfolio.dto.projectDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProjectResponseDTO {

    private Long id;
    private String username;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String thumbnail;
    private String url;
}
