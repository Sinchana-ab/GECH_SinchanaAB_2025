package com.registration.dto;

import java.time.LocalDate;

import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

public class RegistrationDTO {
	
	
	@NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;

    @NotNull(message = "Date of birth cannot be empty")
    private LocalDate dob;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "Gender cannot be empty")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be 'Male', 'Female', or 'Other'")
    private String gender;

    @NotEmpty(message = "At least one skill must be selected")
    private List<String> skills;

    @NotBlank(message = "Address cannot be empty")
    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;


    public RegistrationDTO() {}

    public RegistrationDTO( String name, String email, String phone, int age, LocalDate dob, String city, String gender, List<String> skills, String address) {
        
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.dob = dob;
        this.city = city;
        this.gender = gender;
        this.skills = skills;
        this.address = address;
    }

    // Getters and Setters
    

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
