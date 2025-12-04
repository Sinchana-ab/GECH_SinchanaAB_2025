package com.coursemanagement.dto;

import lombok.Data;

@Data
public class ExamDTO {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private String title;
    private String description;
    private Integer timeLimitMinutes;
    private Integer passingScore;
    private Integer maxAttempts;
    private Integer totalMarks;
    private boolean published;
    private boolean isFinalExam;
    private Integer questionCount; // ✅ Make sure this field exists
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTimeLimitMinutes() {
		return timeLimitMinutes;
	}
	public void setTimeLimitMinutes(Integer timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}
	public Integer getPassingScore() {
		return passingScore;
	}
	public void setPassingScore(Integer passingScore) {
		this.passingScore = passingScore;
	}
	public Integer getMaxAttempts() {
		return maxAttempts;
	}
	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
	public Integer getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public boolean isFinalExam() {
		return isFinalExam;
	}
	public void setFinalExam(boolean isFinalExam) {
		this.isFinalExam = isFinalExam;
	}
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}
    
   
}