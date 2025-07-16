package com.db.relationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.relationships.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {}