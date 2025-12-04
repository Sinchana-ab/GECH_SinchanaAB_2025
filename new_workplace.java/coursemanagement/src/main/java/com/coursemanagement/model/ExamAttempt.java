package com.coursemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // ✅ CHANGED from LocalDate

@Entity
@Table(name = "exam_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamAttempt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt; // ✅ FIXED: Changed from LocalDate
    
    @Column(name = "answers", columnDefinition = "TEXT")
    private String answers;
    
    @Column(name = "score")
    private Integer score;
    
    @Column(name = "total_marks")
    private Integer totalMarks;
    
    @Column(name = "percentage")
    private Double percentage;
    
    @Column(name = "passed")
    private boolean passed = false;
    
    @Column(name = "attempt_number")
    private Integer attemptNumber = 1;
    
    @Column(name = "status")
    private String status = "IN_PROGRESS";
    
    @Column(name = "time_taken_minutes")
    private Integer timeTakenMinutes;
    
    @PrePersist
    protected void onCreate() {
        if (startedAt == null) {
            startedAt = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        if (submittedAt != null && totalMarks != null && totalMarks > 0) {
            percentage = (score.doubleValue() / totalMarks) * 100;
            if (exam != null && exam.getPassingScore() != null) {
                passed = percentage >= exam.getPassingScore();
            }
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
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

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
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