package com.construction.companyManagement.controller;


// ... (other imports)

import com.construction.companyManagement.service.ProjectService; // Import your ProjectService

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.construction.companyManagement.model.Blog;
import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.model.Testimonial;
import com.construction.companyManagement.service.BlogService;    
import com.construction.companyManagement.service.TestimonialService; 
import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/dynamic")
public class DynamicContentController {

    @Autowired
    private ProjectService projectService; // Autowire your ProjectService
    @Autowired
    private BlogService blogService;       // Autowire your BlogService
    @Autowired
    private TestimonialService testimonialService; // Autowire your TestimonialService
    // @Autowired
    // private GalleryService galleryService; // Autowire your GalleryService

    @GetMapping("/projects")
    public String showDynamicProjects(Model model) {
        // --- THIS IS THE KEY CHANGE ---
        // Call your ProjectService to get all projects from the database
        List<Project> projects = projectService.getAllProjects(); 
        // --- END OF KEY CHANGE ---

      //	  model.addAttribute("projects", projects);
        return "dynamic/projects";
    }

    @GetMapping("/blogs")
    public String showDynamicBlogs(Model model) {
        // --- THIS IS THE KEY CHANGE ---
        // Call your BlogService to get all blogs from the database
        List<Blog> blogs = blogService.getAllBlogs(); 
        // --- END OF KEY CHANGE ---

       // model.addAttribute("blogs", blogs);
        return "dynamic/blogs";
    }

    @GetMapping("/testimonials")
    public String showDynamicTestimonials(Model model) {
        // --- THIS IS THE KEY CHANGE ---
        // Call your TestimonialService to get all testimonials from the database
        List<Testimonial> testimonials = testimonialService.getAllTestimonials(); 
        // --- END OF KEY CHANGE ---

        //model.addAttribute("testimonials", testimonials);
        return "dynamic/testimonials";
    }

    @GetMapping("/gallery")
    public String showDynamicGallery(Model model) {
        // --- THIS IS THE KEY CHANGE ---
        // Assuming you have a GalleryService and a method to get all gallery items
        // List<Gallery> galleryItems = galleryService.getAllImages(); // Uncomment and use your actual service call
        // --- END OF KEY CHANGE ---

        // For now, keeping mock data if GalleryService isn't fully set up yet
        List<Object> galleryItems = Arrays.asList( 
            new Object() { 
                public Long getId() { return 1L; }
                public String getTitle() { return "Site Progress"; }
                public String getImageUrl() { return "/images/gallery1.jpg"; }
            },
            new Object() {
                public Long getId() { return 2L; }
                public String getTitle() { return "Architectural Design"; }
                public String getImageUrl() { return "/images/gallery2.jpg"; }
            },
            new Object() {
                public Long getId() { return 3L; }
                public String getTitle() { return "Team at Work"; }
                public String getImageUrl() { return "/images/gallery3.jpg"; }
            }
        );
      //  model.addAttribute("galleryItems", galleryItems);
        return "dynamic/gallery";
    }
}