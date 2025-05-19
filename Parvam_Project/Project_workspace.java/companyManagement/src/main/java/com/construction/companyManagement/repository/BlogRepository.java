package com.construction.companyManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.companyManagement.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findTop3ByOrderByPublishedAtDesc();
}
