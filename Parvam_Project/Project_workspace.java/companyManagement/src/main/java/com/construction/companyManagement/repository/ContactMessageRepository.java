package com.construction.companyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.companyManagement.model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
