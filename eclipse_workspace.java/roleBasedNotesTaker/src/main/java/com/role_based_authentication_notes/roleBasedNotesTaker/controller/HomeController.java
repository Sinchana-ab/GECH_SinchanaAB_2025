package com.role_based_authentication_notes.roleBasedNotesTaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.role_based_authentication_notes.roleBasedNotesTaker.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	@GetMapping({"","/","/dashboard"})
	public String dashboard(HttpServletRequest request, Model model) {
		String success_msg = (String) request.getSession().getAttribute("success");
		if(success_msg!=null) {
			model.addAttribute("success",success_msg);
			request.getSession().removeAttribute("success");
		}
	    return "dashboard";
	}
	@GetMapping("/login")
	public String login() {
	    return "login"; // Returns the view named "login.html"
	}
	@GetMapping("/register")
	public String registration(Model model) {
		model.addAttribute("userDTO", new UserDTO());
	    return "registration"; // Returns the view named "register.html"
	}
	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}
	@GetMapping("/profile")
	public String profile() {
		return "profile";
	}
}
