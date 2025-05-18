package com.myfolio.repository;

import com.myfolio.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);

    List<UserSkill> findByUserId(Long userId);

    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);
}
