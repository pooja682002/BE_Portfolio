package com.example.portfoliotask_backend.repository;

import com.example.portfoliotask_backend.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, UUID> {
}
