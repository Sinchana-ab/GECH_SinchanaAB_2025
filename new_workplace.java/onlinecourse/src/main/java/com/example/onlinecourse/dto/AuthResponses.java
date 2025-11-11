package com.example.onlinecourse.dto;

public class AuthResponses {
    public static class JwtResponse {
        public String token;
        public String type = "Bearer";
        public Long id;
        public String name;
        public String email;
        public String role;

        public JwtResponse(String token, Long id, String name, String email, String role) {
            this.token = token; this.id = id; this.name = name; this.email = email; this.role = role;
        }
    }

    public static class MessageResponse {
        public String message;
        public MessageResponse(String message){ this.message = message; }
    }
}
