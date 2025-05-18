package com.myfolio.dto.userskill;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSkillRequestDTO {

    private Long userId;
    private Long skillId;
}
