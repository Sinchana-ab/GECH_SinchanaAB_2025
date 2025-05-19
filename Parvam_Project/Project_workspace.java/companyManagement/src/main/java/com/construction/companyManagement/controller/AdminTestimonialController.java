package com.construction.companyManagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.construction.companyManagement.dto.TestimonialDTO;
import com.construction.companyManagement.service.TestimonialService;

@Controller
@RequestMapping("/admin/testimonials")
public class AdminTestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    @GetMapping
    public String showAllTestimonials(Model model) {
        model.addAttribute("testimonials", testimonialService.getAllTestimonials());
        return "admin/all-testimonials";
    }

    @PostMapping("/add")
    public String addTestimonial(@ModelAttribute TestimonialDTO testimonialDTO) {
        try {
            testimonialService.saveTestimonial(testimonialDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/testimonials";
    }
}
