package com.userRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userRelation.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
