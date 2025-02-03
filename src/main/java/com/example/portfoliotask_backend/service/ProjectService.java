package com.example.portfoliotask_backend.service;

import com.example.portfoliotask_backend.dto.ProjectResponseDTO;
import com.example.portfoliotask_backend.model.Project;
import com.example.portfoliotask_backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Save a new project
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    // Fetch all projects and convert them to DTOs
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Fetch a project by ID and convert it to a DTO
    public Optional<ProjectResponseDTO> getProjectById(UUID id) {
        return projectRepository.findById(id).map(this::convertToDTO);
    }

    // Update a project by ID
    public boolean updateProject(UUID id, Project updatedProject) {
        return projectRepository.findById(id).map(existingProject -> {
            existingProject.setTitle(updatedProject.getTitle());
            existingProject.setDescription(updatedProject.getDescription());
            if (updatedProject.getImage() != null) {
                existingProject.setImage(updatedProject.getImage());
            }
            projectRepository.save(existingProject);
            return true;
        }).orElse(false);
    }

    // Delete a project by ID
    public boolean deleteProject(UUID id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convert Project entity to DTO
    private ProjectResponseDTO convertToDTO(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                Base64.getEncoder().encodeToString(project.getImage()) // Convert image to Base64
        );
    }
}