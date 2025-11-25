package com.samreact.GoldenRoyalEmail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final String recipient;

    public EmailSenderService(JavaMailSender mailSender,
                              @Value("${FIRM_EMAIL: samueladeleke302@gmail.com}") String recipient) {
        this.mailSender = mailSender;
        this.recipient = recipient;
    }

    public void sendContactHtml(String name, String fromEmail, String messageBody) throws MessagingException {
        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mime, false, "UTF-8");
        helper.setTo(recipient);
        helper.setReplyTo(fromEmail);
        helper.setSubject("Consultancy Mail from " + name);
        String html = "<h2>New Contact Form Submission</h2>"
                + "<p><strong>Name:</strong> " + escapeHtml(name) + "</p>"
                + "<p><strong>Email:</strong> " + escapeHtml(fromEmail) + "</p>"
                + "<hr>"
                + "<p><strong>Message:</strong></p>"
                + "<p>" + nl2br(escapeHtml(messageBody)) + "</p>";
        helper.setText(html, true);
        mailSender.send(mime);
    }

    // tiny helpers to keep email safe-ish
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt").replace(">", "&gt");
    }

    private  String nl2br(String s) {
        return s.replace("\n", "<br/>");
    }
}
