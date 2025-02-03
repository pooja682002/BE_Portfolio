package com.example.portfoliotask_backend.service;

import com.example.portfoliotask_backend.dto.SkillDTO;
import com.example.portfoliotask_backend.model.Skill;
import com.example.portfoliotask_backend.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    // Add a new skill
    public SkillDTO addSkill(String name, byte[] logo) {
        Skill skill = new Skill();
        skill.setName(name);
        skill.setLogo(logo);

        Skill savedSkill = skillRepository.save(skill);
        return convertToDTO(savedSkill);
    }

    // Update an existing skill
    public SkillDTO updateSkill(UUID id, String name, byte[] logo) {
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setName(name);
        skill.setLogo(logo);

        Skill updatedSkill = skillRepository.save(skill);
        return convertToDTO(updatedSkill);
    }

    // Delete a skill by id
    public void deleteSkill(UUID id) {
        skillRepository.deleteById(id);
    }

    // Get a skill by id
    public Optional<Skill> getSkillById(UUID id) {
        return skillRepository.findById(id);  // Fetch the skill by its UUID
    }

    // Get all skills
    public List<SkillDTO> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Convert Skill to SkillDTO
    private SkillDTO convertToDTO(Skill skill) {
        SkillDTO dto = new SkillDTO();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        dto.setLogoBase64(Base64.getEncoder().encodeToString(skill.getLogo()));
        return dto;
    }
}
