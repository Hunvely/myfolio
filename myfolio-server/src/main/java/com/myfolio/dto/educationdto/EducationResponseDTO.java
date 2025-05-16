package com.myfolio.dto.educationdto;

import com.myfolio.enums.EducationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EducationResponseDTO {

    private Long id;
    private String username;
    private String schoolName;
    private EducationStatus status;
    private String major;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
}
