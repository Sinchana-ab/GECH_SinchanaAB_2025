// dto/UserDTO.java
package com.springsecurity.assignment1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
	@Email(message="Enter a valid email")
	@NotBlank(message="email is required")
    private String username;
	@NotBlank(message="password is required")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
