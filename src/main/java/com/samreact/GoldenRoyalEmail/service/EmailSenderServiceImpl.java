package com.samreact.GoldenRoyalEmail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private final String recipient;

    public EmailSenderServiceImpl(JavaMailSender mailSender,
                                   @Value("${firm.recipient}") String recipient) {
        this.mailSender = mailSender;
        this.recipient = recipient;
    }

    @Override
    public void sendContactHtml(String name, String fromEmail, String messageBody) throws MessagingException {
        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mime, false, "UTF-8");
        helper.setTo(recipient);
        helper.setReplyTo(fromEmail);
        helper.setSubject("Consultancy Mail from " + name);
        String html = "<div style='font-family: Inter, Arial, sans-serif; background:#f7f9fb; padding:40px 0;'>"
                + "  <div style='max-width:600px; margin:0 auto; background:white; border-radius:12px; "
                + "       padding:30px; box-shadow:0 4px 20px rgba(0,0,0,0.06);'>"
                + "    <h2 style='margin-top:0; font-size:24px; color:#111;'>New Contact Form Submission</h2>"

                + "    <div style='margin-top:20px;'>"
                + "      <p style='margin:6px 0;'><strong style='color:#555;'>Name:</strong><br>"
                +            escapeHtml(name) + "</p>"

                + "      <p style='margin:6px 0;'><strong style='color:#555;'>Email:</strong><br>"
                +            escapeHtml(fromEmail) + "</p>"
                + "    </div>"

                + "    <hr style='border:0; border-top:1px solid #eee; margin:25px 0;'>"

                + "    <p style='margin:0 0 8px; font-weight:600; color:#555;'>Message:</p>"
                + "    <div style='background:#f1f3f5; padding:15px 18px; border-radius:8px; "
                + "         line-height:1.6; color:#333; white-space:pre-wrap;'>"
                +          nl2br(escapeHtml(messageBody))
                + "    </div>"

                + "    <p style='margin-top:30px; font-size:12px; color:#999; text-align:center;'>"
                + "      You received this email via your website contact form."
                + "    </p>"
                + "  </div>"
                + "</div>";
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
