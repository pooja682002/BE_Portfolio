package com.example.portfoliotask_backend.service;

import com.example.portfoliotask_backend.model.User;
import com.example.portfoliotask_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import java.util.Collections;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User admin = adminRepo.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }


        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), Collections.emptyList());
    }
}
