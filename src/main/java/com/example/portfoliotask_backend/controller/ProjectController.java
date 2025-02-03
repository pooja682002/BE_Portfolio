package com.example.portfoliotask_backend.controller;

import com.example.portfoliotask_backend.dto.ProjectResponseDTO;
import com.example.portfoliotask_backend.model.Project;
import com.example.portfoliotask_backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Upload a new project with an image
    @PostMapping("/upload")
    public ResponseEntity<?> uploadProject(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {
        try {
            Project project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            project.setImage(image.getBytes());

            // Save the project
            projectService.saveProject(project);

            return ResponseEntity.ok("Project uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error uploading image!");
        }
    }

    // Get all projects as DTOs
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Get a project by ID as a DTO
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable UUID id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a project by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(
            @PathVariable UUID id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Project updatedProject = new Project();
            updatedProject.setTitle(title);
            updatedProject.setDescription(description);

            if (image != null && !image.isEmpty()) {
                updatedProject.setImage(image.getBytes());
            }

            boolean isUpdated = projectService.updateProject(id, updatedProject);

            if (isUpdated) {
                return ResponseEntity.ok("Project updated successfully!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error updating project!");
        }
    }

    // Delete a project by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable UUID id) {
        boolean isDeleted = projectService.deleteProject(id);
        if (isDeleted) {
            return ResponseEntity.ok("Project deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}