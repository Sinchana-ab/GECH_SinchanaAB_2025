package com.coursemanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.coursemanagement.model.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:Course Management System}")
    private String appName;

    @Value("${app.url:http://localhost:3000}")
    private String appUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send welcome email after registration
     */
    @Async
    public void sendWelcomeEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to " + appName);

            String htmlContent = buildWelcomeEmailTemplate(user);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send welcome email: " + e.getMessage());
        }
    }

    /**
     * Send enrollment confirmation email
     */
    @Async
    public void sendEnrollmentConfirmation(Enrollment enrollment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(enrollment.getStudent().getEmail());
            helper.setSubject("Enrollment Confirmation - " + enrollment.getCourse().getTitle());

            String htmlContent = buildEnrollmentEmailTemplate(enrollment);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send enrollment email: " + e.getMessage());
        }
    }

    /**
     * Send quiz reminder email
     */
    @Async
    public void sendQuizReminder(User student, Quiz quiz, Course course) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(student.getEmail());
            helper.setSubject("Quiz Reminder - " + quiz.getTitle());

            String htmlContent = buildQuizReminderTemplate(student, quiz, course);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send quiz reminder: " + e.getMessage());
        }
    }

    /**
     * Send quiz result notification
     */
    @Async
    public void sendQuizResult(QuizAttempt attempt) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(attempt.getStudent().getEmail());
            helper.setSubject("Quiz Results - " + attempt.getQuiz().getTitle());

            String htmlContent = buildQuizResultTemplate(attempt);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send quiz result: " + e.getMessage());
        }
    }

    /**
     * Send certificate issued notification
     */
    @Async
    public void sendCertificateNotification(Certificate certificate) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(certificate.getStudent().getEmail());
            helper.setSubject("Certificate Issued - " + certificate.getCourse().getTitle());

            String htmlContent = buildCertificateEmailTemplate(certificate);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send certificate email: " + e.getMessage());
        }
    }

    /**
     * Send course update notification
     */
    @Async
    public void sendCourseUpdateNotification(Course course, String updateMessage) {
        try {
            // Get all enrolled students
            course.getEnrollments().forEach(enrollment -> {
                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);

                    helper.setFrom(fromEmail);
                    helper.setTo(enrollment.getStudent().getEmail());
                    helper.setSubject("Course Update - " + course.getTitle());

                    String htmlContent = buildCourseUpdateTemplate(course, updateMessage, enrollment.getStudent());
                    helper.setText(htmlContent, true);

                    mailSender.send(message);
                } catch (MessagingException e) {
                    System.err.println("Failed to send course update email: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Failed to send course updates: " + e.getMessage());
        }
    }

    /**
     * Send password reset email
     */
    @Async
    public void sendPasswordResetEmail(User user, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Password Reset Request");

            String htmlContent = buildPasswordResetTemplate(user, resetToken);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
        }
    }

    /**
     * Send instructor notification for new enrollment
     */
    @Async
    public void sendInstructorEnrollmentNotification(Enrollment enrollment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(enrollment.getCourse().getInstructor().getEmail());
            helper.setSubject("New Enrollment - " + enrollment.getCourse().getTitle());

            String htmlContent = buildInstructorEnrollmentTemplate(enrollment);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send instructor notification: " + e.getMessage());
        }
    }

    // ==================== HTML Email Templates ====================

    private String buildWelcomeEmailTemplate(User user) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); 
                             color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .button { display: inline-block; padding: 12px 30px; background: #667eea; 
                             color: white; text-decoration: none; border-radius: 5px; margin: 20px 0; }
                    .footer { text-align: center; margin-top: 30px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Welcome to %s!</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Thank you for joining our learning platform! We're excited to have you on board.</p>
                        <p>Your account has been successfully created. You can now:</p>
                        <ul>
                            <li>Browse thousands of courses</li>
                            <li>Enroll in courses that interest you</li>
                            <li>Track your learning progress</li>
                            <li>Earn certificates upon completion</li>
                        </ul>
                        <center>
                            <a href="%s" class="button">Start Learning</a>
                        </center>
                        <p>If you have any questions, feel free to reach out to our support team.</p>
                        <p>Happy Learning!</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(appName, user.getName(), appUrl + "/courses", appName);
    }

    private String buildEnrollmentEmailTemplate(Enrollment enrollment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String enrollmentDate = enrollment.getEnrolledAt().format(formatter);

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #28a745; color: white; padding: 30px; text-align: center; 
                             border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .course-info { background: white; padding: 20px; border-left: 4px solid #28a745; 
                                  margin: 20px 0; }
                    .button { display: inline-block; padding: 12px 30px; background: #28a745; 
                             color: white; text-decoration: none; border-radius: 5px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✓ Enrollment Confirmed!</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Congratulations! You have successfully enrolled in:</p>
                        <div class="course-info">
                            <h3>%s</h3>
                            <p><strong>Instructor:</strong> %s</p>
                            <p><strong>Enrollment Date:</strong> %s</p>
                            <p><strong>Duration:</strong> %d hours</p>
                        </div>
                        <p>You can now start learning at your own pace. Access your course materials anytime!</p>
                        <center>
                            <a href="%s/my-courses" class="button">Go to My Courses</a>
                        </center>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                enrollment.getStudent().getName(),
                enrollment.getCourse().getTitle(),
                enrollment.getCourse().getInstructor().getName(),
                enrollmentDate,
                enrollment.getCourse().getDurationHours(),
                appUrl
            );
    }

    private String buildQuizReminderTemplate(User student, Quiz quiz, Course course) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #ffc107; color: #333; padding: 30px; text-align: center; 
                             border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .quiz-info { background: white; padding: 20px; border-left: 4px solid #ffc107; }
                    .button { display: inline-block; padding: 12px 30px; background: #ffc107; 
                             color: #333; text-decoration: none; border-radius: 5px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>⏰ Quiz Reminder</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Don't forget! You have a quiz pending in your course:</p>
                        <div class="quiz-info">
                            <h3>%s</h3>
                            <p><strong>Course:</strong> %s</p>
                            <p><strong>Time Limit:</strong> %d minutes</p>
                            <p><strong>Passing Score:</strong> %d%%</p>
                        </div>
                        <p>Make sure you're prepared before starting the quiz. Good luck!</p>
                        <center>
                            <a href="%s/quiz/%d" class="button">Take Quiz Now</a>
                        </center>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                student.getName(),
                quiz.getTitle(),
                course.getTitle(),
                quiz.getTimeLimitMinutes(),
                quiz.getPassingScore(),
                appUrl,
                quiz.getId()
            );
    }

    private String buildQuizResultTemplate(QuizAttempt attempt) {
        String resultColor = attempt.isPassed() ? "#28a745" : "#dc3545";
        String resultText = attempt.isPassed() ? "Congratulations! You Passed!" : "Quiz Completed";
        String resultEmoji = attempt.isPassed() ? "🎉" : "📝";

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: %s; color: white; padding: 30px; text-align: center; 
                             border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .score-box { background: white; padding: 30px; text-align: center; 
                                border-radius: 10px; margin: 20px 0; }
                    .score { font-size: 48px; font-weight: bold; color: %s; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s %s</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>You have completed the quiz: <strong>%s</strong></p>
                        <div class="score-box">
                            <div class="score">%.1f%%</div>
                            <p>Score: %d / %d points</p>
                        </div>
                        <p>%s</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                resultColor,
                resultColor,
                resultEmoji,
                resultText,
                attempt.getStudent().getName(),
                attempt.getQuiz().getTitle(),
                attempt.getPercentage(),
                attempt.getScore(),
                attempt.getTotalPoints(),
                attempt.isPassed() 
                    ? "Great job! You've demonstrated excellent understanding of the material." 
                    : "Keep learning! You can retake the quiz to improve your score."
            );
    }

    private String buildCertificateEmailTemplate(Certificate certificate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String issueDate = certificate.getIssuedAt().format(formatter);

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #f093fb 0%%, #f5576c 100%%); 
                             color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .cert-info { background: white; padding: 20px; border: 3px solid #f5576c; 
                                border-radius: 10px; margin: 20px 0; }
                    .button { display: inline-block; padding: 12px 30px; 
                             background: linear-gradient(135deg, #f093fb 0%%, #f5576c 100%%); 
                             color: white; text-decoration: none; border-radius: 5px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🏆 Certificate Issued!</h1>
                    </div>
                    <div class="content">
                        <h2>Congratulations, %s!</h2>
                        <p>We're thrilled to inform you that your certificate has been issued for:</p>
                        <div class="cert-info">
                            <h3>%s</h3>
                            <p><strong>Final Score:</strong> %.2f%%</p>
                            <p><strong>Issue Date:</strong> %s</p>
                            <p><strong>Certificate Number:</strong> %s</p>
                        </div>
                        <p>You can download your certificate and share it on LinkedIn or other platforms!</p>
                        <center>
                            <a href="%s/certificates" class="button">Download Certificate</a>
                        </center>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                certificate.getStudent().getName(),
                certificate.getCourse().getTitle(),
                certificate.getFinalScore(),
                issueDate,
                certificate.getCertificateNumber(),
                appUrl
            );
    }

    private String buildCourseUpdateTemplate(Course course, String updateMessage, User student) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #17a2b8; color: white; padding: 30px; text-align: center; }
                    .content { background: #f9f9f9; padding: 30px; }
                    .update-box { background: white; padding: 20px; border-left: 4px solid #17a2b8; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>📢 Course Update</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>There's an update for your enrolled course:</p>
                        <div class="update-box">
                            <h3>%s</h3>
                            <p>%s</p>
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(student.getName(), course.getTitle(), updateMessage);
    }

    private String buildPasswordResetTemplate(User user, String resetToken) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #dc3545; color: white; padding: 30px; text-align: center; }
                    .content { background: #f9f9f9; padding: 30px; }
                    .button { display: inline-block; padding: 12px 30px; background: #dc3545; 
                             color: white; text-decoration: none; border-radius: 5px; }
                    .warning { background: #fff3cd; padding: 15px; border-left: 4px solid #ffc107; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🔐 Password Reset Request</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>We received a request to reset your password. Click the button below to create a new password:</p>
                        <center>
                            <a href="%s/reset-password?token=%s" class="button">Reset Password</a>
                        </center>
                        <div class="warning">
                            <p><strong>Security Notice:</strong> This link will expire in 1 hour. 
                            If you didn't request this, please ignore this email.</p>
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(user.getName(), appUrl, resetToken);
    }

    private String buildInstructorEnrollmentTemplate(Enrollment enrollment) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #6c757d; color: white; padding: 30px; text-align: center; }
                    .content { background: #f9f9f9; padding: 30px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>👤 New Student Enrollment</h1>
                    </div>
                    <div class="content">
                        <p>A new student has enrolled in your course:</p>
                        <p><strong>Student:</strong> %s</p>
                        <p><strong>Course:</strong> %s</p>
                        <p><strong>Date:</strong> %s</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                enrollment.getStudent().getName(),
                enrollment.getCourse().getTitle(),
                enrollment.getEnrolledAt().toString()
            );
    }
}