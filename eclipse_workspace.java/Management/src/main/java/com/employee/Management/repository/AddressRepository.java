package com.employee.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Management.model.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}