package com.construction.companyManagement.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.construction.companyManagement.dto.BlogDTO;
import com.construction.companyManagement.model.Blog;
import com.construction.companyManagement.repository.BlogRepository;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepo;

    public void saveBlog(BlogDTO dto) throws IOException {
        Blog blog = new Blog();
        blog.setTitle(dto.getTitle());
        blog.setAuthor(dto.getAuthor());
        blog.setContent(dto.getContent());
        blog.setPublishedAt(LocalDate.now());

        String fileName = dto.getImage().getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/blog-images/");
        if (!Files.exists(path)) Files.createDirectories(path);
        Files.copy(dto.getImage().getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        blog.setImagePath(fileName);
        blogRepo.save(blog);
    }

    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }
    public void updateBlog(Long id, String title, String author, String content, MultipartFile image) {
        Blog blog = blogRepo.findById(id).orElse(null);
        if (blog != null) {
            blog.setTitle(title);
            blog.setAuthor(author);
            blog.setContent(content);

            if (image != null && !image.isEmpty()) {
                String fileName = image.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/blog-images/");
                try {
                    Files.copy(image.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    blog.setImagePath(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            blogRepo.save(blog);
        }
    }

	public void deleteBlog(Long id) {
		blogRepo.deleteById(id);
		
	}

	public List<Blog> getBlogById(Long id) {
		// TODO Auto-generated method stub
		return blogRepo.findAll();
	}

}
