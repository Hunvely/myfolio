package com.myfolio.dto.skilldto;

import com.myfolio.enums.SkillType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillRequestDTO {

    private String skillName;
    private SkillType skillType;
    private String logoUrl;
}
