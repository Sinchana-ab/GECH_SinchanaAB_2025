package com.role_based_authentication_notes.roleBasedNotesTaker.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.role_based_authentication_notes.roleBasedNotesTaker.dto.RoleDTO;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Role;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.User;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class RoleService {
	
	private RoleRepository roleRepository;
	

	public RoleService(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}



	public void saveRole(RoleDTO roleDTO) {
		Role role = new Role();
		role.setName(roleDTO.getName().toUpperCase());
		roleRepository.save(role);
	}



	public void updateRole(@Valid RoleDTO roleDTO, Long id) {
		System.out.println("id: "+id);
	    Role existingRole = roleRepository.findById(id)
	        .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
	    System.out.println(existingRole+"----- exist");
	    System.out.println("roledto"+ roleDTO);
	    existingRole.setName(roleDTO.getName().toUpperCase());
	    roleRepository.save(existingRole);
	}



	public void deleteRole(Long id) {
		Role role = roleRepository.findById(id)
		        .orElseThrow(() -> new EntityNotFoundException("Role not found"));
	    // Remove the role from all users
		for( User user : role.getUsers()) {
			user.getRoles().remove(role);
		}
	    roleRepository.delete(role);
	}

}
