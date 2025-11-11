package com.example.onlinecourse.dto;

public class AuthRequests {
    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }
}
