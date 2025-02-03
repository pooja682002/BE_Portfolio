package com.example.portfoliotask_backend.dto;

import java.util.UUID;

public class ProjectResponseDTO {

    private UUID id;
    private String title;
    private String description;
    private String imageBase64;

    // Constructor
    public ProjectResponseDTO(UUID id, String title, String description, String imageBase64) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageBase64 = imageBase64;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}