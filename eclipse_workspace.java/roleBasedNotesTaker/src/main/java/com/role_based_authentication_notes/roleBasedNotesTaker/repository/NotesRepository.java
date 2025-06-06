package com.role_based_authentication_notes.roleBasedNotesTaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.role_based_authentication_notes.roleBasedNotesTaker.model.Notes;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.User;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
	 List<Notes> findByUser(User user);
}
