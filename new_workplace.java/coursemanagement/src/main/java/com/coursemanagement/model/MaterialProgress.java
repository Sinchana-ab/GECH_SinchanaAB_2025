
package com.coursemanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "material_progress")
@Data
public class MaterialProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private CourseMaterial material;

    @Column(name = "is_completed")
    private boolean completed = false;

    @Column(name = "watch_time_seconds")
    private Integer watchTimeSeconds = 0;

    @Column(name = "total_duration_seconds")
    private Integer totalDurationSeconds;

    @Column(name = "completion_percentage")
    private Double completionPercentage = 0.0;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
        lastAccessedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastAccessedAt = LocalDateTime.now();
        
        // Calculate completion percentage
        if (totalDurationSeconds != null && totalDurationSeconds > 0) {
            completionPercentage = (watchTimeSeconds.doubleValue() / totalDurationSeconds) * 100;
            
            // Mark as completed if watched 90% or more
            if (completionPercentage >= 90 && !completed) {
                completed = true;
                completedAt = LocalDateTime.now();
            }
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public CourseMaterial getMaterial() {
		return material;
	}

	public void setMaterial(CourseMaterial material) {
		this.material = material;
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