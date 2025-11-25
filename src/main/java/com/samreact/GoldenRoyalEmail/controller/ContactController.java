package com.samreact.GoldenRoyalEmail.controller;

import com.samreact.GoldenRoyalEmail.DTO.ContactRequest;
import com.samreact.GoldenRoyalEmail.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")

public class ContactController {
    private final EmailSenderService emailService;

    public ContactController(EmailSenderService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> sendContact(@Valid @RequestBody ContactRequest request) {
        try {
            emailService.sendContactHtml(request.getName(), request.getEmail(), request.getMessage());
            return ResponseEntity.ok().body("{\"status\":\"sent\"}");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\":\"failed to send email\"}");
        }
    }
}
