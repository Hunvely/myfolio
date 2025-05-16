package com.myfolio.service;

import com.myfolio.dto.skilldto.SkillRequestDTO;
import com.myfolio.dto.skilldto.SkillResponseDTO;
import com.myfolio.entity.Skill;
import com.myfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public Skill toEntity(SkillRequestDTO skillRequestDTO) {
        return Skill.builder()
                .skillName(skillRequestDTO.getSkillName())
                .skillType(skillRequestDTO.getSkillType())
                .logoUrl(skillRequestDTO.getLogoUrl())
                .build();
    }

    public SkillResponseDTO toDTO(Skill skill) {
        return SkillResponseDTO.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .skillType(skill.getSkillType())
                .logoUrl(skill.getLogoUrl())
                .build();
    }

    public SkillResponseDTO createSkill(SkillRequestDTO skillRequestDTO) {
        Skill skill = toEntity(skillRequestDTO);
        Skill savedSkill = skillRepository.save(skill);
        return toDTO(savedSkill);
    }

    public Skill findSkillEntityById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found with id: " + id));
    }

    public SkillResponseDTO getSkill(Long id) {
        return toDTO(findSkillEntityById(id));
    }

    public List<SkillResponseDTO> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public SkillResponseDTO updateSkill(Long id, SkillRequestDTO skillRequestDTO) {
        Skill skill = findSkillEntityById(id);
        skill.setSkillName(skillRequestDTO.getSkillName());
        skill.setSkillType(skillRequestDTO.getSkillType());
        skill.setLogoUrl(skillRequestDTO.getLogoUrl());
        Skill savedSkill = skillRepository.save(skill);
        return toDTO(savedSkill);
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new IllegalArgumentException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }
}
