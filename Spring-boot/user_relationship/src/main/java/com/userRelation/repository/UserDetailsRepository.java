package com.userRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userRelation.models.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}
