package com.employee.Management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employee.Management.model.Role;
import com.employee.Management.model.User;
import com.employee.Management.repository.RoleRepository;
import com.employee.Management.repository.UserRepository;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner setupDefaults(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            if (roleRepo.findByName("ADMIN").isEmpty()) {
                roleRepo.save(new Role("ADMIN"));
            }
            if (roleRepo.findByName("USER").isEmpty()) {
                roleRepo.save(new Role("USER"));
            }

            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRoles(Set.of(roleRepo.findByName("ADMIN").orElseThrow()));
                userRepo.save(admin);
                System.out.println("✅ Default admin user created! [admin/admin123]");
            }
        };
    }
}