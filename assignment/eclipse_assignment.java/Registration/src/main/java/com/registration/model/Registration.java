package com.registration.model;

import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String phone; 
    private int age;
    private LocalDate dob;
    private String city;
    private String gender;

    @ElementCollection
    private List<String> skills;

    private String address;

    // Default constructor
    public Registration() {}

    public Registration(long id, String name, String email, String phone, int age, LocalDate dob, String city, String gender, List<String> skills, String address) {
        this.id = id;
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
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

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
    public void setSkills(List<String> skill) { this.skills = skills; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}