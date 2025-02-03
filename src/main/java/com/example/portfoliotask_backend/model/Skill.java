package com.example.portfoliotask_backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Skill {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Lob
    private byte[] logo;

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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
