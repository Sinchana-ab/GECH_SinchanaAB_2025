package com.example.onlinecourse.dto;

import com.example.onlinecourse.model.Course;

import java.util.function.Function;

public class CourseDto {

    public static class CreateRequest {
        public String title;
        public String description;
        public Boolean published;
        // <-- admin will provide instructor email to assign ownership
        public String instructorEmail;
    }

    public static class UpdateRequest {
        public String title;
        public String description;
        public Boolean published;
    }

    public static class Response {
        public Long id;
        public String title;
        public String description;
        public boolean published;
        public Long instructorId;
        public String instructorName;
        public String instructorEmail;

        public static Response fromEntity(Course c) {
            Response r = new Response();
            r.id = c.getId();
            r.title = c.getTitle();
            r.description = c.getDescription();
            r.published = c.isPublished();
            if (c.getInstructor() != null) {
                r.instructorId = c.getInstructor().getId();
                r.instructorName = c.getInstructor().getName();
                r.instructorEmail = c.getInstructor().getEmail();
            }
            return r;
        }
    }
}
