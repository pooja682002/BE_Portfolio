package com.example.portfoliotask_backend.controller;

import com.example.portfoliotask_backend.dto.AdminDTO;
import com.example.portfoliotask_backend.model.Admin;
import com.example.portfoliotask_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerAdmin(@RequestBody AdminDTO adminDTO) {
        String result = adminService.registerAdmin(adminDTO);

        if (result.equals("User already exists")) {
            return ResponseEntity.status(400).body(new ResponseMessage(400, "Admin already exists", null));
        } else if (result.equals("User registered successfully")) {
            return ResponseEntity.status(201).body(new ResponseMessage(201, "Admin registered successfully", adminDTO));
        } else {
            return ResponseEntity.status(500).body(new ResponseMessage(500, "Something went wrong", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginAdmin() {
        return ResponseEntity.ok(new ResponseMessage(200, "Login Successful", null));
    }

    @GetMapping
    public ResponseEntity<Object> getAllAdmins() {
        return ResponseEntity.ok(new ResponseMessage(200, "Admins fetched successfully", adminService.getAllAdmins()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAdminById(@PathVariable UUID id) {
        Optional<Admin> admin = adminService.getAdminById(id);

        if (admin.isPresent()) {
            return ResponseEntity.ok(new ResponseMessage(200, "Admin fetched successfully", admin.get()));
        } else {
            return ResponseEntity.status(404).body(new ResponseMessage(404, "Admin not found", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAdmin(@PathVariable UUID id, @RequestBody AdminDTO adminDTO) {
        String result = adminService.updateAdmin(id, adminDTO);

        if (result.equals("Admin not found")) {
            return ResponseEntity.status(404).body(new ResponseMessage(404, "Admin not found", null));
        } else if (result.equals("Admin updated successfully")) {
            return ResponseEntity.ok(new ResponseMessage(200, "Admin updated successfully", adminDTO));
        } else {
            return ResponseEntity.status(500).body(new ResponseMessage(500, "Something went wrong", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable UUID id) {
        String result = adminService.deleteAdmin(id);

        if (result.equals("Admin not found")) {
            return ResponseEntity.status(404).body(new ResponseMessage(404, "Admin not found", null));
        } else if (result.equals("Admin deleted successfully")) {
            return ResponseEntity.ok(new ResponseMessage(200, "Admin deleted successfully", null));
        } else {
            return ResponseEntity.status(500).body(new ResponseMessage(500, "Something went wrong", null));
        }
    }

    public static class ResponseMessage {
        private int statusCode;
        private String msg;
        private Object response;

        public ResponseMessage(int statusCode, String msg, Object response) {
            this.statusCode = statusCode;
            this.msg = msg;
            this.response = response;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMsg() {
            return msg;
        }

        public Object getResponse() {
            return response;
        }
    }
}
