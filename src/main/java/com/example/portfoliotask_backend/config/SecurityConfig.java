package com.example.portfoliotask_backend.config;

import com.example.portfoliotask_backend.service.AdminDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Ensures password is hashed properly
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AdminDetailsService adminDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // Ensure password comparison is done properly
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/admin/register", "/api/admin/login",

                                // Skills Endpoints
                                "/api/skills", "/api/skills/{id}",

                                // Education Endpoints
                                "/api/education", "/api/education/{id}",

                                // Projects Endpoints
                                "/api/projects", "/api/projects/{id}", "/api/projects/upload"
                        ).permitAll()
                        .requestMatchers("/api/skills/**").authenticated() // Require authentication for all skill-related endpoints
                        .requestMatchers("/api/education/**").authenticated() // Require authentication for all education-related endpoints
                        .requestMatchers("/api/projects/**").authenticated() // Require authentication for all project-related endpoints
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Enable Basic Authentication

        return http.build();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Add authentication manager configuration if needed
    }
}
