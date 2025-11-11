package com.coursemanagement.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialProgressDTO {
    private Long id;
    private Long studentId;
    private Long enrollmentId;
    private Long materialId;
    private String materialTitle;
    private String materialType;
    private boolean completed;
    private Integer watchTimeSeconds;
    private Integer totalDurationSeconds;
    private Double completionPercentage;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime lastAccessedAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getMaterialTitle() {
		return materialTitle;
	}
	public void setMaterialTitle(String materialTitle) {
		this.materialTitle = materialTitle;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Integer getWatchTimeSeconds() {
		return watchTimeSeconds;
	}
	public void setWatchTimeSeconds(Integer watchTimeSeconds) {
		this.watchTimeSeconds = watchTimeSeconds;
	}
	public Integer getTotalDurationSeconds() {
		return totalDurationSeconds;
	}
	public void setTotalDurationSeconds(Integer totalDurationSeconds) {
		this.totalDurationSeconds = totalDurationSeconds;
	}
	public Double getCompletionPercentage() {
		return completionPercentage;
	}
	public void setCompletionPercentage(Double completionPercentage) {
		this.completionPercentage = completionPercentage;
	}
	public LocalDateTime getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}
	public LocalDateTime getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}
	public LocalDateTime getLastAccessedAt() {
		return lastAccessedAt;
	}
	public void setLastAccessedAt(LocalDateTime lastAccessedAt) {
		this.lastAccessedAt = lastAccessedAt;
	}
	
}
