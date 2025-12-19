package com.coursemanagement.service;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.mail.SimpleMailMessage; // REMOVED: Unused import
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

    /**
     * ✅ Send certificate email with congratulations (New, detailed version)
     */
    @Async
    public void sendCertificateEmail(Certificate certificate) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(certificate.getStudent().getEmail());
            helper.setSubject("🎉 Congratulations! You Earned a New Certificate - " + certificate.getCourse().getTitle());
            
            String htmlContent = buildCertificateEmailTemplate(certificate);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            
            System.out.println("✅ Certificate email sent successfully to: " + certificate.getStudent().getEmail());
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send certificate email: " + e.getMessage());
            e.printStackTrace();
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
                             color: white; 
                             padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .button { display: inline-block; padding: 12px 30px; background: #667eea; 
                             color: white; 
                             text-decoration: none; border-radius: 5px; margin: 20px 0; }
                    .footer { text-align: center; margin-top: 30px;
                             color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                  
                       <h1>Welcome to %s!</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Thank you 
                            for joining our learning platform! We're excited to have you on board.</p>
                        <p>Your account has been successfully created.
                            You can now:</p>
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
                        <p>&copy;
                            2024 %s. All rights reserved.</p>
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
                    body 
                        { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px;
                        margin: 0 auto; padding: 20px; }
                    .header { background: #28a745;
                        color: white; padding: 30px; text-align: center; 
                             border-radius: 10px 10px 0 0;
                        }
                    .content { background: #f9f9f9;
                        padding: 30px; border-radius: 0 0 10px 10px; }
                    .course-info { background: white;
                        padding: 20px; border-left: 4px solid #28a745; 
                                  margin: 20px 0; }
                    .button { display: inline-block;
                        padding: 12px 30px; background: #28a745; 
                             color: white; text-decoration: none; border-radius: 5px;
                        }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                      
                        <h1>✓ Enrollment Confirmed!</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Congratulations!
                            You have successfully enrolled in:</p>
                        <div class="course-info">
                            <h3>%s</h3>
                            <p><strong>Instructor:</strong> %s</p>
              
                            <p><strong>Enrollment Date:</strong> %s</p>
                            <p><strong>Duration:</strong> %d hours</p>
                        </div>
                        <p>You can now start learning at 
                            your own pace. Access your course materials anytime!</p>
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
                    body { font-family: Arial, sans-serif;
                        line-height: 1.6; color: #333; }
                    .container { max-width: 600px;
                        margin: 0 auto; padding: 20px; }
                    .header { background: #ffc107;
                        color: #333; padding: 30px; text-align: center; 
                             border-radius: 10px 10px 0 0;
                        }
                    .content { background: #f9f9f9;
                        padding: 30px; border-radius: 0 0 10px 10px; }
                    .quiz-info { background: white;
                        padding: 20px; border-left: 4px solid #ffc107; }
                    .button { display: inline-block;
                        padding: 12px 30px; background: #ffc107; 
                             color: #333; text-decoration: none; border-radius: 5px;
                        }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                      
                        <h1>⏰ Quiz Reminder</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Don't forget!
                            You have a quiz pending in your course:</p>
                        <div class="quiz-info">
                            <h3>%s</h3>
                            <p><strong>Course:</strong> %s</p>
           
                            <p><strong>Time Limit:</strong> %d minutes</p>
                            <p><strong>Passing Score:</strong> %d%%</p>
                        </div>
                        <p>Make sure 
                            you're prepared before starting the quiz. Good luck!</p>
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
                   
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333;
                        }
                    .container { max-width: 600px;
                        margin: 0 auto; padding: 20px; }
                    .header { background: %s;
                        color: white; padding: 30px; text-align: center; 
                             border-radius: 10px 10px 0 0;
                        }
                    .content { background: #f9f9f9;
                        padding: 30px; border-radius: 0 0 10px 10px; }
                    .score-box { background: white;
                        padding: 30px; text-align: center; 
                                border-radius: 10px; margin: 20px 0; }
                    .score { font-size: 48px;
                        font-weight: bold; color: %s; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                  
                       <h1>%s %s</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>You have completed 
                            the quiz: <strong>%s</strong></p>
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
                    ?
                        "Great job! You've demonstrated excellent understanding of the material." 
                    : "Keep learning! You can retake the quiz to improve your score."
            );
    }

    private String buildCourseUpdateTemplate(Course course, String updateMessage, User student) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 
                        1.6; color: #333; }
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
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: 
                        #333; }
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
                        <p>We received a request to reset your password.
                            Click the button below to create a new password:</p>
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
                    .container { max-width: 600px;
                        margin: 0 auto; padding: 20px; }
                    .header { background: #6c757d;
                        color: white; padding: 30px; text-align: center; }
                    .content { background: #f9f9f9;
                        padding: 30px; }
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
    
    /**
     * Build beautiful certificate email template
     */
    private String buildCertificateEmailTemplate(Certificate certificate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String issueDate = certificate.getIssuedAt().format(formatter);

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { 
                    
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
                        line-height: 1.6; 
                        color: #333; 
                        margin: 0;
            
                        padding: 0;
                        background-color: #f4f4f4;
                    }
                    .email-wrapper {
                     
                        max-width: 600px;
                        margin: 20px auto;
                        background: white;
                        border-radius: 10px;
                    
                        overflow: hidden;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                    }
                    .header { 
                        background: 
                            linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                        color: white; 
                        padding: 40px 30px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 32px;
                        font-weight: bold;
                    }
                    .header .emoji {
                        font-size: 60px;
                        margin-bottom: 10px;
                    }
                    .content { 
                        padding: 40px 30px;
                        background: white;
                    }
                    .congratulations {
                        text-align: center;
                        margin-bottom: 30px;
                    }
                    .congratulations h2 {
                        color: #667eea;
                        font-size: 28px;
                        margin-bottom: 10px;
                    }
                    .certificate-box { 
                        background: linear-gradient(135deg, #f093fb 0%%, #f5576c 100%%);
                        padding: 30px;
                        border-radius: 15px;
                        margin: 30px 0;
                        color: white;
                        text-align: center;
                        box-shadow: 0 6px 20px rgba(245, 87, 108, 0.3);
                        }
                    .certificate-box h3 {
                        margin: 0 0 15px 0;
                        font-size: 24px;
                        font-weight: bold;
                    }
                    .certificate-box .course-title {
                        font-size: 20px;
                        margin: 15px 0;
                        font-weight: bold;
                        text-transform: uppercase;
                        letter-spacing: 1px;
                    }
                    .stats {
                        display: table;
                        width: 100%%;
                        margin: 20px 0;
                    }
                    .stat-item {
                        display: table-cell;
                        text-align: center;
                        padding: 15px;
                    }
                    .stat-value {
                        font-size: 32px;
                        font-weight: bold;
                        color: #667eea;
                        display: block;
                    }
                    .stat-label {
                        font-size: 14px;
                        color: #666;
                        margin-top: 5px;
                    }
                    .cert-details {
                        background: #f8f9fa;
                        padding: 20px;
                        border-radius: 10px;
                        margin: 20px 0;
                    }
                    .cert-details p {
                        margin: 10px 0;
                        font-size: 15px;
                    }
                    .cert-details strong {
                        color: #667eea;
                        font-weight: 600;
                    }
                    .button-container {
                        text-align: center;
                        margin: 30px 0;
                    }
                    .button { 
                        display: inline-block;
                        padding: 15px 40px;
                        background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                        color: white;
                        text-decoration: none;
                        border-radius: 50px;
                        font-weight: bold;
                        font-size: 16px;
                        box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
                        transition: all 0.3s ease;
                        }
                    .button:hover {
                        box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
                        transform: translateY(-2px);
                    }
                    .share-section {
                        background: #fff3cd;
                        border-left: 4px solid #ffc107;
                        padding: 20px;
                        margin: 20px 0;
                        border-radius: 5px;
                        }
                    .share-section h4 {
                        margin: 0 0 10px 0;
                        color: #856404;
                    }
                    .share-section p {
                        margin: 5px 0;
                        color: #856404;
                    }
                    .footer { 
                        text-align: center;
                        padding: 30px;
                        background: #f8f9fa;
                        color: #666;
                        font-size: 13px;
                    }
                    .footer a {
                        color: #667eea;
                        text-decoration: none;
                    }
                    .divider {
                        height: 2px;
                        background: linear-gradient(90deg, transparent, #667eea, transparent);
                        margin: 30px 0;
                    }
                </style>
            </head>
            <body>
                <div class="email-wrapper">
                    <div class="header">
              
                        <div class="emoji">🏆</div>
                        <h1>You Earned a New Certificate!</h1>
                    </div>
                    
                    <div 
                        class="content">
                        <div class="congratulations">
                            <h2>Congratulations, %s!</h2>
                            <p>You've successfully completed the course and earned your certificate.</p>
          
                        </div>

                        <div class="certificate-box">
                            <h3>Certificate of Completion</h3>
                            <div class="divider" style="background: 
                                rgba(255,255,255,0.3);"></div>
                            <div class="course-title">%s</div>
                            <div class="divider" style="background: rgba(255,255,255,0.3);"></div>
                        </div>

                
                        <div class="stats">
                            <div class="stat-item">
                                <span class="stat-value">%.1f%%</span>
                             
                                <span class="stat-label">Final Score</span>
                            </div>
                            <div class="stat-item">
                                <span class="stat-value">100%%</span>
     
                                <span class="stat-label">Course Progress</span>
                            </div>
                        </div>

                   
                        <div class="cert-details">
                            <p><strong>🎓 Student:</strong> %s</p>
                            <p><strong>📚 Course:</strong> %s</p>
                            <p><strong>👨‍🏫 Instructor:</strong> %s</p>
    
                            <p><strong>📅 Issued:</strong> %s</p>
                            <p><strong>🔢 Certificate Number:</strong> %s</p>
                        </div>

                   
                        <div class="button-container">
                            <a href="%s/student/certificates" class="button">
                                📥 Download Your Certificate
                            </a>
 
                        </div>

                        <div class="share-section">
                            <h4>💡 Share Your Achievement!</h4>
                     
                            <p>✓ Add this certificate to your LinkedIn profile</p>
                            <p>✓ Share it on social media</p>
                            <p>✓ Include it in your resume or portfolio</p>
                  
                        </div>

                        <div class="divider"></div>

                        <p style="text-align: center;
                            color: #666; font-size: 14px;">
                            This certificate demonstrates your commitment to learning and professional development. 
                            Keep up the great work!
                        </p>
   
                        </div>
                    
                    <div class="footer">
                        <p><strong>%s</strong></p>
                  
                        <p>Continue your learning journey at <a href="%s">%s</a></p>
                        <p style="margin-top: 15px;
                            font-size: 11px; color: #999;">
                            This certificate can be verified at: %s/verify/%s
                        </p>
                        <p style="margin-top: 20px;">&copy; 2024 %s. All rights reserved.</p>
        
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                certificate.getStudent().getName(),
                certificate.getCourse().getTitle(),
    
                certificate.getFinalScore(),
                certificate.getStudent().getName(),
                certificate.getCourse().getTitle(),
                certificate.getCourse().getInstructor().getName(),
                issueDate,
                certificate.getCertificateNumber(),
        
                appUrl,
                appName,
                appUrl,
                appName,
                appUrl,
                certificate.getCertificateNumber(),
            
                appName
            );
    }

}