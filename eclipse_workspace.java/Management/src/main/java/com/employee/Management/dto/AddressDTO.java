package com.employee.Management.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressDTO {

@NotBlank(message = "Street is required")
private String street;

@NotBlank(message = "City is required")
private String city;

@NotBlank(message = "State is required")
private String state;

// Getters and Setters

public String getStreet() {
    return street;
}

public void setStreet(String street) {
    this.street = street;
}

public String getCity() {
    return city;
}

public void setCity(String city) {
    this.city = city;
}

public String getState() {
    return state;
}

public void setState(String state) {
    this.state = state;
}
}