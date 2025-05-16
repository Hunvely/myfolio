package com.myfolio.controller;

import com.myfolio.dto.educationdto.EducationRequestDTO;
import com.myfolio.dto.educationdto.EducationResponseDTO;
import com.myfolio.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/educations")
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponseDTO> createEducation(@RequestBody EducationRequestDTO educationRequestDTO) {
        return ResponseEntity.ok(educationService.createEducation(educationRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationResponseDTO> getEducation(@PathVariable Long id) {
        return ResponseEntity.ok(educationService.getEducation(id));
    }

    @GetMapping
    public ResponseEntity<List<EducationResponseDTO>> getAllEducations() {
        return ResponseEntity.ok(educationService.getAllEducations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationResponseDTO> updateEducation(@PathVariable Long id, @RequestBody EducationRequestDTO educationDTO) {
        return ResponseEntity.ok(educationService.updateEducation(id, educationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }
}
