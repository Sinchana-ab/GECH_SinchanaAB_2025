package com.db.relationships.model;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity

@Table(name="messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "attachment_id")
	private Attachment attachment;
	
	@ManyToMany
	@JoinColumn(name = "sender_id", referencedColumnName = "user_id")
	private User sender;
	
	
}
