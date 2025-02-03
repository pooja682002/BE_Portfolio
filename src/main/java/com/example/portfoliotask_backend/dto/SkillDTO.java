package com.example.portfoliotask_backend.dto;

import java.util.UUID;

public class SkillDTO {
    private UUID id;
    private String name;
    private String logoBase64; // To send the logo as a Base64 string

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoBase64() {
        return logoBase64;
    }

    public void setLogoBase64(String logoBase64) {
        this.logoBase64 = logoBase64;
    }
}
