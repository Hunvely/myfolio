package com.myfolio.dto.careerdto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CareerResponseDTO {

    private Long id;
    private String username;
    private String companyName;
    private String location;
    private String employmentType;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
