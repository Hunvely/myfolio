package com.myfolio.service;

import com.myfolio.dto.userskill.UserSkillRequestDTO;
import com.myfolio.dto.userskill.UserSkillResponseDTO;
import com.myfolio.entity.Skill;
import com.myfolio.entity.User;
import com.myfolio.entity.UserSkill;
import com.myfolio.repository.SkillRepository;
import com.myfolio.repository.UserRepository;
import com.myfolio.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserSkillResponseDTO addUserSkill(UserSkillRequestDTO request) {
        if (userSkillRepository.existsByUserIdAndSkillId(request.getUserId(), request.getSkillId())) {
            throw new IllegalArgumentException("이미 등록된 스킬입니다.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스킬입니다."));

        UserSkill userSkill = UserSkill.builder()
                .user(user)
                .skill(skill)
                .build();

        UserSkill saved = userSkillRepository.save(userSkill);

        return toDto(saved);
    }

    public List<UserSkillResponseDTO> getSkillsByUserId(Long userId) {
        return userSkillRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public void deleteUserSkill(Long userId, Long skillId) {
        UserSkill userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 스킬이 없습니다."));

        userSkillRepository.delete(userSkill);
    }

    private UserSkillResponseDTO toDto(UserSkill userSkill) {
        return UserSkillResponseDTO.builder()
                .id(userSkill.getId())
                .username(userSkill.getUser().getUsername())
                .skillName(userSkill.getSkill().getSkillName())
                .build();
    }
}
