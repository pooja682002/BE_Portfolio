package com.example.portfoliotask_backend.controller;

import com.example.portfoliotask_backend.dto.EducationDTO;
import com.example.portfoliotask_backend.model.Education;
import com.example.portfoliotask_backend.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/education")
@CrossOrigin(origins = "*")
public class EducationController {

    @Autowired
    private EducationService educationService;

    // Add a new education record
    @PostMapping
    public ResponseEntity<Map<String, Object>> addEducation(
            @RequestParam("degree") String degree,
            @RequestParam("institution") String institution,
            @RequestParam("year") String year,
            @RequestParam("logo") MultipartFile logo) {
        try {
            byte[] logoBytes = logo.getBytes();
            EducationDTO savedEducation = educationService.addEducation(degree, institution, year, logoBytes);
            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "message", "Education record added successfully",
                    "response", savedEducation
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "statusCode", 400,
                    "message", e.getMessage(),
                    "response", null
            ));
        }
    }

    // Get all education records
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEducation() {
        List<EducationDTO> educationList = educationService.getAllEducation();
        return ResponseEntity.ok(Map.of(
                "statusCode", 200,
                "message", "Education records fetched successfully",
                "response", educationList
        ));
    }

    // Get education by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEducationById(@PathVariable UUID id) {
        return educationService.getEducationById(id)
                .map(education -> ResponseEntity.ok(Map.of(
                        "statusCode", 200,
                        "message", "Education record fetched successfully",
                        "response", education
                )))
                .orElse(ResponseEntity.status(404).body(Map.of(
                        "statusCode", 404,
                        "message", "Education record not found",
                        "response", null
                )));
    }

    // Update an education record
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEducation(
            @PathVariable UUID id,
            @RequestParam("degree") String degree,
            @RequestParam("institution") String institution,
            @RequestParam("year") String year,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {
        try {
            byte[] logoBytes = logo != null ? logo.getBytes() : null;
            EducationDTO updatedEducation = educationService.updateEducation(id, degree, institution, year, logoBytes);
            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "message", "Education record updated successfully",
                    "response", updatedEducation
            ));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                    "statusCode", 400,
                    "message", e.getMessage(),
                    "response", null
            ));
        }
    }

    // Delete an education record
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEducation(@PathVariable UUID id) {
        boolean isDeleted = educationService.deleteEducation(id);
        if (isDeleted) {
            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "message", "Education record deleted successfully",
                    "response", null
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "statusCode", 404,
                    "message", "Education record not found",
                    "response", null
            ));
        }
    }
}
