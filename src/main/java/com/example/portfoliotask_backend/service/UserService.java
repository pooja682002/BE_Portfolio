package com.example.portfoliotask_backend.service;

import com.example.portfoliotask_backend.dto.AdminDTO;
import com.example.portfoliotask_backend.model.Admin;
import com.example.portfoliotask_backend.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new admin
    public String registerAdmin(AdminDTO adminDTO) {
        if (adminRepo.findByUsername(adminDTO.getUsername()) != null) {
            return "User already exists";
        }

        String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());

        Admin admin = new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(hashedPassword);
        adminRepo.save(admin);

        return "User registered successfully";
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    // Get admin by ID
    public Optional<Admin> getAdminById(UUID id) {
        return adminRepo.findById(id);
    }

    // Update admin details
    public String updateAdmin(UUID id, AdminDTO adminDTO) {
        Optional<Admin> existingAdmin = adminRepo.findById(id);

        if (!existingAdmin.isPresent()) {
            return "Admin not found";
        }

        Admin admin = existingAdmin.get();
        admin.setUsername(adminDTO.getUsername());

        // If the password is provided, update it
        if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        }

        adminRepo.save(admin);
        return "Admin updated successfully";
    }

    // Delete admin
    public String deleteAdmin(UUID id) {
        Optional<Admin> existingAdmin = adminRepo.findById(id);

        if (!existingAdmin.isPresent()) {
            return "Admin not found";
        }

        adminRepo.deleteById(id);
        return "Admin deleted successfully";
    }
}
