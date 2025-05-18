package com.myfolio.dto.userskill;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSkillResponseDTO {

    private Long id;
    private String username;
    private String skillName;
}
