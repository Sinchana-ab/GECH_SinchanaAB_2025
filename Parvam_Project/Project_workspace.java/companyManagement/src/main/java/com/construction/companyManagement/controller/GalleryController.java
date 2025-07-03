package com.construction.companyManagement.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.construction.companyManagement.dto.GalleryDTO;
import com.construction.companyManagement.service.GalleryService;

@Controller
@RequestMapping("/admin/gallery")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @GetMapping
    public String showGallery(Model model) {
        model.addAttribute("galleryItems", galleryService.getAllImages());
        return "admin/gallery-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("galleryDTO", new GalleryDTO());
        return "admin/gallery-add";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("galleryDTO", galleryService.getImageById(id));
        return "admin/gallery-edit";
    }


    @PostMapping("/save")
    public String saveImage(@ModelAttribute GalleryDTO galleryDTO) {
        try {
            galleryService.saveImage(galleryDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/gallery";
    }

    @GetMapping("/delete")
    public String deleteImage(@RequestParam("id") Long id) {
        galleryService.deleteImage(id);
        return "redirect:/admin/gallery";
    }
}
