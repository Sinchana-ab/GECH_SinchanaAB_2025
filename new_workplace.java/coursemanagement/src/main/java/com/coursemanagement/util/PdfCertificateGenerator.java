package com.coursemanagement.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.coursemanagement.model.Certificate;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating PDF certificates
 * Uses iText library for PDF generation
 */
public class PdfCertificateGenerator {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 40, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font HEADING_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK);
    private static final Font NAME_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD, BaseColor.BLUE);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC, BaseColor.GRAY);

    /**
     * Generate a PDF certificate
     * 
     * @param certificate Certificate entity with student and course details
     * @return byte array of the generated PDF
     */
    public static byte[] generateCertificate(Certificate certificate) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // Create document with landscape orientation
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, baos);
        
        document.open();
        
        // Add border
        addBorder(document);
        
        // Add certificate content
        addCertificateContent(document, certificate);
        
        document.close();
        
        return baos.toByteArray();
    }

    /**
     * Add decorative border to the certificate
     */
    private static void addBorder(Document document) throws DocumentException {
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(3);
        rect.setBorderColor(new BaseColor(0, 102, 204)); // Blue border
        document.add(rect);
    }

    /**
     * Add main content to the certificate
     */
    private static void addCertificateContent(Document document, Certificate certificate) throws DocumentException {
        // Add spacing from top
        addEmptyLine(document, 3);

        // Certificate Title
        Paragraph title = new Paragraph("Certificate of Completion", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        addEmptyLine(document, 2);

        // "This is to certify that" text
        Paragraph certifyText = new Paragraph("This is to certify that", NORMAL_FONT);
        certifyText.setAlignment(Element.ALIGN_CENTER);
        document.add(certifyText);
        
        addEmptyLine(document, 1);

        // Student Name
        Paragraph studentName = new Paragraph(certificate.getStudent().getName(), NAME_FONT);
        studentName.setAlignment(Element.ALIGN_CENTER);
        document.add(studentName);
        
        addEmptyLine(document, 1);

        // "has successfully completed" text
        Paragraph completedText = new Paragraph("has successfully completed the course", NORMAL_FONT);
        completedText.setAlignment(Element.ALIGN_CENTER);
        document.add(completedText);
        
        addEmptyLine(document, 1);

        // Course Title
        Paragraph courseTitle = new Paragraph(certificate.getCourse().getTitle(), HEADING_FONT);
        courseTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(courseTitle);
        
        addEmptyLine(document, 2);

        // Score and Date
        Paragraph scoreText = new Paragraph(
            String.format("Final Score: %.2f%%", certificate.getFinalScore()), 
            NORMAL_FONT
        );
        scoreText.setAlignment(Element.ALIGN_CENTER);
        document.add(scoreText);
        
        addEmptyLine(document, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        Paragraph dateText = new Paragraph(
            "Date of Completion: " + certificate.getIssuedAt().format(formatter), 
            NORMAL_FONT
        );
        dateText.setAlignment(Element.ALIGN_CENTER);
        document.add(dateText);
        
        addEmptyLine(document, 2);

        // Add line separator
        LineSeparator line = new LineSeparator();
        line.setLineWidth(1);
        line.setLineColor(BaseColor.GRAY);
        document.add(new Chunk(line));
        
        addEmptyLine(document, 1);

        // Instructor signature section
        Paragraph instructorName = new Paragraph(
            certificate.getCourse().getInstructor().getName(), 
            NORMAL_FONT
        );
        instructorName.setAlignment(Element.ALIGN_CENTER);
        document.add(instructorName);

        Paragraph instructorTitle = new Paragraph("Course Instructor", SMALL_FONT);
        instructorTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(instructorTitle);
        
        addEmptyLine(document, 2);

        // Certificate Number
        Paragraph certNumber = new Paragraph(
            "Certificate Number: " + certificate.getCertificateNumber(), 
            SMALL_FONT
        );
        certNumber.setAlignment(Element.ALIGN_CENTER);
        document.add(certNumber);

        // Verification text
        Paragraph verifyText = new Paragraph(
            "Verify this certificate at: www.yoursite.com/verify/" + certificate.getCertificateNumber(), 
            SMALL_FONT
        );
        verifyText.setAlignment(Element.ALIGN_CENTER);
        document.add(verifyText);
    }

    /**
     * Add empty lines for spacing
     */
    private static void addEmptyLine(Document document, int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
            document.add(new Paragraph(" "));
        }
    }

    /**
     * Generate a simple text-based certificate (for testing without iText)
     */
    public static String generateTextCertificate(Certificate certificate) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("                  CERTIFICATE OF COMPLETION                    \n");
        sb.append("═══════════════════════════════════════════════════════════════\n\n");
        sb.append("                    This is to certify that                    \n\n");
        sb.append("                  ").append(certificate.getStudent().getName()).append("\n\n");
        sb.append("            has successfully completed the course              \n\n");
        sb.append("                  ").append(certificate.getCourse().getTitle()).append("\n\n");
        sb.append("───────────────────────────────────────────────────────────────\n\n");
        sb.append("Final Score: ").append(String.format("%.2f%%", certificate.getFinalScore())).append("\n");
        sb.append("Date: ").append(certificate.getIssuedAt().toString()).append("\n");
        sb.append("Certificate Number: ").append(certificate.getCertificateNumber()).append("\n\n");
        sb.append("                  ").append(certificate.getCourse().getInstructor().getName()).append("\n");
        sb.append("                      Course Instructor                        \n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        
        return sb.toString();
    }
}