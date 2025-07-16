package com.employee.Management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeDTO {

@NotBlank(message = "Employee name is required")
private String name;

@Email(message = "Valid email is required")
@NotBlank(message = "Email is required")
private String email;

@NotNull(message = "Department ID is required")
private Long departmentId;

private AddressDTO address; // Optional, if you want to save Address with Employee

// Getters and Setters

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public Long getDepartmentId() {
    return departmentId;
}

public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
}

public AddressDTO getAddress() {
    return address;
}

public void setAddress(AddressDTO address) {
    this.address = address;
}
}