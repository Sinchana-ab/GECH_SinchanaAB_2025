package com.coursemanagement.dto;

import lombok.Data;
import java.time.LocalDateTime; // ✅ CHANGED from LocalDate

@Data
public class ExamAttemptDTO {
    private Long id;
    private Long examId;
    private String examTitle;
    private Long studentId;
    private String studentName;
    private Long enrollmentId;
    private LocalDateTime startedAt;
    private LocalDateTime submittedAt; // ✅ FIXED: Changed from LocalDate
    private Integer score;
    private Integer totalMarks;
    private Double percentage;
    private boolean passed;
    private Integer attemptNumber;
    private String status;
    private Integer timeTakenMinutes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public String getExamTitle() {
		return examTitle;
	}
	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public LocalDateTime getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}
	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}
	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public boolean isPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	public Integer getAttemptNumber() {
		return attemptNumber;
	}
	public void setAttemptNumber(Integer attemptNumber) {
		this.attemptNumber = attemptNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTimeTakenMinutes() {
		return timeTakenMinutes;
	}
	public void setTimeTakenMinutes(Integer timeTakenMinutes) {
		this.timeTakenMinutes = timeTakenMinutes;
	}
    
    
}