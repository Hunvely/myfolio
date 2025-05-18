package com.myfolio.controller;

import com.myfolio.dto.userskill.UserSkillRequestDTO;
import com.myfolio.dto.userskill.UserSkillResponseDTO;
import com.myfolio.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-skills")
public class UserSkillController {

    private final UserSkillService userSkillService;

    @PostMapping
    public ResponseEntity<UserSkillResponseDTO> addUserSkill(@RequestBody UserSkillRequestDTO request) {
        return ResponseEntity.ok(userSkillService.addUserSkill(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserSkillResponseDTO>> getSkillsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userSkillService.getSkillsByUserId(userId));
    }

    @DeleteMapping("/{userId}/{skillId}")
    public ResponseEntity<Void> deleteUserSkill(@RequestParam Long userId, @RequestParam Long skillId) {
        userSkillService.deleteUserSkill(userId, skillId);
        return ResponseEntity.noContent().build();
    }
}
