package com.myfolio.service;

import com.myfolio.entity.Project;
import com.myfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project project = getProject(id);
        project.setTitle(updatedProject.getTitle());
        project.setDescription(updatedProject.getDescription());
        return projectRepository.save(project);
    }

    public void deleteUser(Long id) {
        projectRepository.deleteById(id);
    }
}
