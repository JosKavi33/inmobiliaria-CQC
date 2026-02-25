package com.jose.inmobiliaria.property.user_service.application.service;

import com.jose.inmobiliaria.property.user_service.domain.entity.User;
import com.jose.inmobiliaria.property.user_service.domain.enums.RoleType;
import com.jose.inmobiliaria.property.user_service.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${CREATE_DEFAULT_ADMIN:false}")
    private boolean createAdmin;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!createAdmin) {
            return;
        }

        if (userRepository.findByEmail(adminEmail).isEmpty()) {

            log.info("Checking if default admin should be created...");
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRoleType(RoleType.ADMIN);
            admin.setEnabled(true);

            userRepository.save(admin);
            log.info("Default admin created successfully");
        }
    }
}