package com.example.portfoliotask_backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue
    private UUID id;

    private String degree;

    private String institution;

    private String year;

    @Lob
    private byte[] logo;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
