package com.db.relationships.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
}
