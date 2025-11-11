package com.coursemanagement.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Generate admin password
        System.out.println("Admin Password: " + encoder.encode("Admin@123"));
        
        // Generate instructor password
        System.out.println("Instructor Password: " + encoder.encode("Instructor@123"));
        
        // Generate student password
        System.out.println("Student Password: " + encoder.encode("Student@123"));
    }
}