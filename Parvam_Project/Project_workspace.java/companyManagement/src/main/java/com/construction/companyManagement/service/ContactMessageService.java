package com.construction.companyManagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.construction.companyManagement.dto.ContactMessageDTO;
import com.construction.companyManagement.model.ContactMessage;
import com.construction.companyManagement.repository.ContactMessageRepository;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepo;

    public void saveMessage(ContactMessageDTO dto) {
        ContactMessage msg = new ContactMessage();
        msg.setName(dto.getName());
        msg.setEmail(dto.getEmail());
        msg.setSubject(dto.getSubject());
        msg.setMessage(dto.getMessage());
        msg.setSubmittedAt(LocalDate.now());

        contactMessageRepo.save(msg);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepo.findAll();
    }
}
