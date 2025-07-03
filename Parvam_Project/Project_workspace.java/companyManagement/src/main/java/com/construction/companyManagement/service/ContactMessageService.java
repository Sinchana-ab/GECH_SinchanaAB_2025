package com.construction.companyManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.construction.companyManagement.model.ContactMessage;
import com.construction.companyManagement.repository.ContactMessageRepository;

import java.util.List;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository messageRepository;

    public List<ContactMessage> getAllMessages() {
        return messageRepository.findAll();
    }

    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

	public void save(ContactMessage contactMessage) {
		// TODO Auto-generated method stub
		
	}
}