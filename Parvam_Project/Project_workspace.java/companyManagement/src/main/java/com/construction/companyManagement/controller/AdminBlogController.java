package com.construction.companyManagement.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.construction.companyManagement.dto.BlogDTO;
import com.construction.companyManagement.model.Blog;
import com.construction.companyManagement.service.BlogService;

@Controller
@RequestMapping("/admin/blogs")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String showAllBlogs(Model model) {
        model.addAttribute("blogs", blogService.getAllBlogs());
        return "admin/all-blogs";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("blogDTO", new BlogDTO());
        return "admin/blogs-add";
    }

    @PostMapping("/add")
    public String addBlog(@ModelAttribute BlogDTO blogDTO) {
        try {
            blogService.saveBlog(blogDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        List<Blog> blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "admin/blogs-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBlog(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String author,
                              @RequestParam String content,
                              @RequestParam(required = false) MultipartFile image) {

        blogService.updateBlog(id, title, author, content, image);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/admin/blogs";
    }
}
