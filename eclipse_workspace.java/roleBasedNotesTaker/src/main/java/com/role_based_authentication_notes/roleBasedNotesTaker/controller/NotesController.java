package com.role_based_authentication_notes.roleBasedNotesTaker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.role_based_authentication_notes.roleBasedNotesTaker.dto.NotesDTO;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Notes;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.User;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.NotesRepository;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.UserRepository;
import com.role_based_authentication_notes.roleBasedNotesTaker.services.NotesService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NotesController {

	private NotesRepository notesRepository;
	private UserRepository userRepository;
	private NotesService notesService;

	public NotesController(NotesRepository notesRepository, UserRepository userRepository, NotesService notesService) {
		super();
		this.notesRepository = notesRepository;
		this.userRepository = userRepository;
		this.notesService = notesService;
	}

	@GetMapping({"/",""})
	public String notes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		var authorities = userDetails.getAuthorities();
		if(authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
			List<Notes> notes = notesRepository.findAll();
			model.addAttribute("notes", notes);
		}else {
			User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
			List<Notes> notes= notesRepository.findByUser(user);
			model.addAttribute("notes",notes);
		}
		return "notes"; // Returns the view named "notes.html"
	}

	@GetMapping("/add-note")
	public String addNote(Model model) {
		model.addAttribute("noteDTO", new NotesDTO());
		return "add-notes";
	}

	@PostMapping("/add-note")
	public String addNote(@Valid @ModelAttribute NotesDTO notesDTO, BindingResult result, Model model,
			RedirectAttributes attributes, @AuthenticationPrincipal UserDetails userDetails) {
		if (result.hasErrors()) {
			return "add-notes";
		}
		String email = userDetails.getUsername();
		User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		notesService.saveNotes(notesDTO, user);
		attributes.addAttribute("success", "Notes saved Successfully");
		return "redirect:/notes";
	}
	
	@GetMapping("/edit-note/{id}")
	public String editNotes(@PathVariable Long id,RedirectAttributes attributes,Model model) {
		Optional<Notes> notes =  notesRepository.findById(id);
		if(!notes.isPresent()) {
			attributes.addFlashAttribute("error","Notes not found");
			return "redirect:/notes";
		}
		NotesDTO notesDTO = new NotesDTO();
		notesDTO.setTitle(notes.get().getTitle());
		notesDTO.setContent(notes.get().getContent());
		model.addAttribute("note",notes.get());
		model.addAttribute("noteDTO",notesDTO);
		return "edit-notes";
	}
	
	@PostMapping("/edit-note/{id}")
	public String updateNotes(@Valid @ModelAttribute NotesDTO notesDTO, BindingResult result ,@PathVariable Long id,Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			Optional<Notes> notes =  notesRepository.findById(id);
			model.addAttribute("note",notes.get());
			return "edit-notes";
		}
		Optional<Notes> notes =  notesRepository.findById(id);
		if(!notes.isPresent()) {
			attributes.addAttribute("error","Notes not found");
			return "redirect:/notes";
		}
		notesService.updateNotes(notesDTO,id);
		attributes.addFlashAttribute("success","Notes updtaed successfully");
		return "redirect:/notes";
	}
}
