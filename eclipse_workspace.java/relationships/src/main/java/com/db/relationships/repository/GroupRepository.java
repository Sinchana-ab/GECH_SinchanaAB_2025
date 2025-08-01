package com.db.relationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.relationships.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {}
