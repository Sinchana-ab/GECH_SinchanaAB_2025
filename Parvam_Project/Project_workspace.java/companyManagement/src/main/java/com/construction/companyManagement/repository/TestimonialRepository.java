package com.construction.companyManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.companyManagement.model.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    List<Testimonial> findTop3ByOrderByDateDesc();
}

