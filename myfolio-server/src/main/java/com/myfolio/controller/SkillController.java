package com.myfolio.controller;

import com.myfolio.dto.skilldto.SkillRequestDTO;
import com.myfolio.dto.skilldto.SkillResponseDTO;
import com.myfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponseDTO> createSkill(@RequestBody SkillRequestDTO skillRequestDTO) {
        return ResponseEntity.ok(skillService.createSkill(skillRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> getSkill(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.getSkill(id));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> updateSkill(@PathVariable Long id, @RequestBody SkillRequestDTO skillRequestDTO) {
        return ResponseEntity.ok(skillService.updateSkill(id, skillRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok().build();
    }
}
