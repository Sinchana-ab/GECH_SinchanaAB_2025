package com.gecCRUD.EmployeeCRUD.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeDTO {
	
	 @NotBlank(message = "First name is required")
	    private String fName;

	    @NotBlank(message = "Last name is required")
	    private String lName;

	    @Email(message = "Enter a valid email")
	    @NotBlank(message = "Email is required")
	    @Column(unique = true, nullable = false)
	    private String email;

	    @Pattern(regexp = "^\\d{10}$", message = "Phone must be 10 digits")
	    private String phone;

	    @NotBlank(message = "Address is required")
	    private String address;
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
