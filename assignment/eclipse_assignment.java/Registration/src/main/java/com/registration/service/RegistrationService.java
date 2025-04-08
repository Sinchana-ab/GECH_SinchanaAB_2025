package com.registration.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.dto.RegistrationDTO;
import com.registration.model.Registration;
import com.registration.repository.RegistrationRepository;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    // Register a new user
    public Registration register(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();
        registration.setName(registrationDTO.getName());
        registration.setEmail(registrationDTO.getEmail());
        registration.setPhone(registrationDTO.getPhone());
        registration.setAge(registrationDTO.getAge());
        registration.setDob(registrationDTO.getDob());
        registration.setCity(registrationDTO.getCity());
        registration.setGender(registrationDTO.getGender());
        registration.setSkills(registrationDTO.getSkills());
        registration.setAddress(registrationDTO.getAddress());

        return registrationRepository.save(registration);
    }

    // Fetch all registered users
    public List<Registration> getAllRegistration() {
        return registrationRepository.findAll();
    }

    // Get user by ID
    public Registration getRegistrationById(long id) {
        Optional<Registration> user = registrationRepository.findById(id);
        return user.orElse(null);
    }

    // Update an existing user
    public void updateRegistration(long id, RegistrationDTO updatedUser) {
        Registration existingUser = getRegistrationById(id);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setCity(updatedUser.getCity());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setSkills(updatedUser.getSkills());
            existingUser.setAddress(updatedUser.getAddress());

            registrationRepository.save(existingUser);
        }
    }

    // Delete user by ID
    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }
}
