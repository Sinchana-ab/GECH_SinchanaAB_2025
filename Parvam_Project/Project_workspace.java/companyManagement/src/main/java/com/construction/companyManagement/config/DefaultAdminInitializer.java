package com.construction.companyManagement.config;

import com.construction.companyManagement.model.Admin;
import com.construction.companyManagement.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultAdminInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DefaultAdminInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if an admin already exists to prevent creating duplicates on every restart
        Optional<Admin> existingAdmin = adminRepository.findByEmail("admin@company.com");

        if (existingAdmin.isEmpty()) {
            Admin defaultAdmin = new Admin();
            defaultAdmin.setName("System Admin");
            defaultAdmin.setEmail("admin@company.com");
            defaultAdmin.setPassword(passwordEncoder.encode("admin123")); // IMPORTANT: Encode the password!
            defaultAdmin.setRole("ADMIN"); // The role is already set to ADMIN in your model, but it's good practice to be explicit.
            
            adminRepository.save(defaultAdmin);
            System.out.println("Default admin user created: admin@company.com");
        } else {
            System.out.println("Default admin user already exists.");
        }
    }
}