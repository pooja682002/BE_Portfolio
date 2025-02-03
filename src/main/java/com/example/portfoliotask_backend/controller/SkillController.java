package com.example.portfoliotask_backend.controller;

import com.example.portfoliotask_backend.dto.SkillDTO;
import com.example.portfoliotask_backend.model.Skill;
import com.example.portfoliotask_backend.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // Add a new skill
    @PostMapping
    public ResponseEntity<?> addSkill(@RequestParam("name") String name, @RequestParam("logo") MultipartFile logo) {
        try {
            byte[] logoBytes = logo.getBytes();
            SkillDTO savedSkill = skillService.addSkill(name, logoBytes);
            return ResponseEntity.ok().body(createResponse(200, "Skill added successfully", savedSkill));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse(400, e.getMessage(), null));
        }
    }

    // Update an existing skill
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable UUID id, @RequestParam("name") String name, @RequestParam("logo") MultipartFile logo) {
        try {
            byte[] logoBytes = logo.getBytes();
            SkillDTO updatedSkill = skillService.updateSkill(id, name, logoBytes);
            return ResponseEntity.ok().body(createResponse(200, "Skill updated successfully", updatedSkill));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse(400, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSkill(@PathVariable UUID id) {
        try {
            // Check if the skill exists
            Optional<Skill> skill = skillService.getSkillById(id);
            if (skill.isPresent()) {
                skillService.deleteSkill(id);
                return ResponseEntity.ok(Map.of(
                        "statusCode", 200,
                        "msg", "Skill deleted successfully",
                        "response", Collections.emptyMap()  // Avoid using null, use an empty map instead
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "statusCode", 404,
                        "msg", "Skill not found",
                        "response", Collections.emptyMap()  // Avoid using null, use an empty map instead
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Internal server error while deleting skill",
                    "response", Collections.emptyMap()  // Avoid using null, use an empty map instead
            ));
        }
    }


    // Get all skills
    @GetMapping
    public ResponseEntity<?> getAllSkills() {
        List<SkillDTO> skills = skillService.getAllSkills();
        return ResponseEntity.ok().body(createResponse(200, "Skills fetched successfully", skills));
    }

    // Helper method for generating responses
    private Map<String, Object> createResponse(int statusCode, String message, Object response) {
        return Map.of(
                "statusCode", statusCode,
                "message", message,
                "response", response
        );
    }

    // Helper method for generating responses without response data (for delete)
    private Map<String, Object> createResponse(String message, Object response) {
        return Map.of(
                "statusCode", 200,
                "message", message,
                "response", response
        );
    }
}
