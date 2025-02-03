package com.example.portfoliotask_backend.service;

import com.example.portfoliotask_backend.dto.EducationDTO;
import com.example.portfoliotask_backend.model.Education;
import com.example.portfoliotask_backend.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    public EducationDTO addEducation(String degree, String institution, String year, byte[] logo) {
        Education education = new Education();
        education.setDegree(degree);
        education.setInstitution(institution);
        education.setYear(year);
        education.setLogo(logo);

        Education savedEducation = educationRepository.save(education);
        return toDTO(savedEducation);
    }

    public List<EducationDTO> getAllEducation() {
        return educationRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<EducationDTO> getEducationById(UUID id) {
        return educationRepository.findById(id).map(this::toDTO);
    }

    public EducationDTO updateEducation(UUID id, String degree, String institution, String year, byte[] logo) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new RuntimeException("Education record not found"));
        education.setDegree(degree);
        education.setInstitution(institution);
        education.setYear(year);
        if (logo != null) {
            education.setLogo(logo);
        }

        Education updatedEducation = educationRepository.save(education);
        return toDTO(updatedEducation);
    }

    public boolean deleteEducation(UUID id) {
        if (educationRepository.existsById(id)) {
            educationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EducationDTO toDTO(Education education) {
        EducationDTO dto = new EducationDTO();
        dto.setId(education.getId());
        dto.setDegree(education.getDegree());
        dto.setInstitution(education.getInstitution());
        dto.setYear(education.getYear());
        dto.setLogo(Base64.getEncoder().encodeToString(education.getLogo()));
        return dto;
    }
}
