package com.userRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userRelation.models.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

}
