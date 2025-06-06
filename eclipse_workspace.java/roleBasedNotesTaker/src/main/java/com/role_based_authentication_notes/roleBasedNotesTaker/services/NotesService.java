package com.role_based_authentication_notes.roleBasedNotesTaker.services;

import org.springframework.stereotype.Service;

import com.role_based_authentication_notes.roleBasedNotesTaker.dto.NotesDTO;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Notes;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.User;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.NotesRepository;

import jakarta.validation.Valid;

@Service
public class NotesService {

	private NotesRepository notesRepository;
	
	
	
	public NotesService(NotesRepository notesRepository) {
		super();
		this.notesRepository = notesRepository;
	}



	public void saveNotes(@Valid NotesDTO notesDTO, User user) {
		Notes notes = new Notes();
		notes.setTitle(notesDTO.getTitle().toUpperCase());
		notes.setContent(notesDTO.getContent());
		notes.setUser(user);
		notesRepository.save(notes);
	}



	public void updateNotes(@Valid NotesDTO notesDTO, Long id) {
		Notes notes = notesRepository.findById(id).get();
		notes.setTitle(notesDTO.getTitle().toUpperCase());
		notes.setContent(notesDTO.getContent());
		notesRepository.save(notes);
	}

}
