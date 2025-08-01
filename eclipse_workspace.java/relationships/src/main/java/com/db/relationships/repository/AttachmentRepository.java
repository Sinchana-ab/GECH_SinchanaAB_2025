package com.db.relationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.relationships.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {}