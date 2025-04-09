package com.project.profile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.profile.dto.ProfileDTO;
import com.project.profile.model.Profile;
import com.project.profile.repository.ProfileRepository;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepository repo;

	public ProfileService(ProfileRepository repo) {
		super();
		this.repo = repo;
	}

	public List<Profile> getallProfile(){
		return repo.findAll();
	}
	public void saveProfile(ProfileDTO dto) {
        Profile p = new Profile();
        p.setName(dto.getName());
        p.setGender(dto.getGender());
        p.setColor(dto.getColor());
        p.setFontSize(dto.getFontSize());
        repo.save(p);
    }
	
	public void deleteProfile(Long id) {
        repo.deleteById(id);
    }
	
	
}
