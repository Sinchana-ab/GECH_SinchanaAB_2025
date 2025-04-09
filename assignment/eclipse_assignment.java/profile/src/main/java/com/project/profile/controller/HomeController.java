package com.project.profile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.profile.dto.ProfileDTO;
import com.project.profile.service.ProfileService;

import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
    private ProfileService service;
	
	@GetMapping({"/","/profiles"})
    public String showProfiles(Model model) {
        model.addAttribute("profiles", service.getallProfile());
        model.addAttribute("profileDTO", new ProfileDTO());
        return "index";
    }
	@PostMapping("/profiles")
    public String saveProfile(@ModelAttribute("profileDTO") @Valid ProfileDTO dto,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("profiles", service.getallProfile());
            return "profile_form";
        }
        service.saveProfile(dto);
        return "redirect:/profiles";
    }

    @PostMapping("/deleteProfile/{id}")
    public String deleteProfile(@PathVariable Long id) {
        service.deleteProfile(id);
        return "redirect:/profiles";
    }

}
