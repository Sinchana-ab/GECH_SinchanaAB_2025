package com.construction.companyManagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.construction.companyManagement.dto.GalleryImageDTO;
import com.construction.companyManagement.service.GalleryImageService;

@Controller
@RequestMapping("/admin/gallery")
public class AdminGalleryController {

    @Autowired
    private GalleryImageService galleryImageService;

    @GetMapping
    public String showAllImages(Model model) {
        model.addAttribute("galleryImages", galleryImageService.getAllImages());
        return "admin/all-gallery";
    }

    @PostMapping("/add")
    public String addImage(@ModelAttribute GalleryImageDTO galleryImageDTO) {
        try {
            galleryImageService.saveGalleryImage(galleryImageDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/gallery";
    }
}

