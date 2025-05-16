package com.myfolio.controller;

import com.myfolio.dto.projectdto.ProjectRequestDTO;
import com.myfolio.dto.projectdto.ProjectResponseDTO;
import com.myfolio.entity.Project;
import com.myfolio.entity.User;
import com.myfolio.repository.UserRepository;
import com.myfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO projectRequestDTO) {
        User user = userRepository.findById(projectRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + projectRequestDTO.getUserId()));
        return ResponseEntity.ok(projectService.createProject(projectRequestDTO, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO updatedProject) {
        return ResponseEntity.ok(projectService.updateProject(id, updatedProject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id) {
        projectService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
