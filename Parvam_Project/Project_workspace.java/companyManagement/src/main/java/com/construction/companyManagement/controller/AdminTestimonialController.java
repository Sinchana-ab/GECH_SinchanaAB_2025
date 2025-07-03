package com.construction.companyManagement.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("testimonialDTO", new TestimonialDTO());
        return "admin/testimonial-add";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        TestimonialDTO dto = testimonialService.getTestimonialById(id);
        model.addAttribute("testimonialDTO", dto);
        return "admin/testimonial-edit";
    }

    @PostMapping("/save")
    public String saveOrUpdateTestimonial(@ModelAttribute TestimonialDTO testimonialDTO) {
        try {
            testimonialService.saveTestimonial(testimonialDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/testimonials";
    }

    @GetMapping("/delete")
    public String deleteTestimonial(@RequestParam("id") Long id) {
        testimonialService.deleteTestimonial(id);
        return "redirect:/admin/testimonials";
    }
}
