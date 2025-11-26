package com.samreact.GoldenRoyalEmail.controller;

import com.samreact.GoldenRoyalEmail.dto.request.ContactRequest;
import com.samreact.GoldenRoyalEmail.dto.response.ContactResponse;
import com.samreact.GoldenRoyalEmail.dto.response.ErrorResponse;
import com.samreact.GoldenRoyalEmail.data.enums.ResponseStatus;
import com.samreact.GoldenRoyalEmail.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
@Slf4j
public class ContactController {
    private final EmailSenderService emailService;

    public ContactController(EmailSenderService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> sendContact(@Valid @RequestBody ContactRequest request) {
        try {
            emailService.sendContactHtml(request.name(), request.email(), request.message());
            return ResponseEntity.ok(new ContactResponse(ResponseStatus.SUCCESS, "Email sent successfully"));
        } catch (MessagingException ex) {
            log.error("An error occurred while sending email: " + ex.getMessage());
            return ResponseEntity.status(500).body(new ErrorResponse(ResponseStatus.ERROR, "Failed to send email"));
        }
    }
}
