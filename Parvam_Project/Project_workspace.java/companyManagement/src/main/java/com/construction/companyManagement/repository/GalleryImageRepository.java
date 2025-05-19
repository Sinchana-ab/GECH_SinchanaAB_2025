package com.construction.companyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.companyManagement.model.GalleryImage;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
}

