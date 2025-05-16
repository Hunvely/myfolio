package com.myfolio.service;

import com.myfolio.dto.projectdto.ProjectRequestDTO;
import com.myfolio.dto.projectdto.ProjectResponseDTO;
import com.myfolio.entity.Project;
import com.myfolio.entity.User;
import com.myfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project toEntity(ProjectRequestDTO projectRequestDTO, User user) {
        return Project.builder()
                .user(user)
                .title(projectRequestDTO.getTitle())
                .description(projectRequestDTO.getDescription())
                .startDate(projectRequestDTO.getStartDate())
                .endDate(projectRequestDTO.getEndDate())
                .thumbnail(projectRequestDTO.getThumbnail())
                .url(projectRequestDTO.getUrl())
                .build();
    }

    public ProjectResponseDTO toDto(Project project) {
        return ProjectResponseDTO.builder()
                .id(project.getId())
                .username(project.getUser().getUsername())
                .title(project.getTitle())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .thumbnail(project.getThumbnail())
                .url(project.getUrl())
                .build();
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO, User user) {
        Project project = toEntity(projectRequestDTO, user);
        Project savedProject = projectRepository.save(project);
        return toDto(savedProject);
    }

    public Project findProjectEntityById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
    }

    public ProjectResponseDTO getProject(Long id) {
        return toDto(findProjectEntityById(id));
    }

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO) {
        Project project = findProjectEntityById(id);
        project.setTitle(projectRequestDTO.getTitle());
        project.setDescription(projectRequestDTO.getDescription());
        project.setStartDate(projectRequestDTO.getStartDate());
        project.setEndDate(projectRequestDTO.getEndDate());
        project.setThumbnail(projectRequestDTO.getThumbnail());
        project.setUrl(projectRequestDTO.getUrl());
        Project updatedProject = projectRepository.save(project);
        return toDto(updatedProject);
    }

    public void deleteUser(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
