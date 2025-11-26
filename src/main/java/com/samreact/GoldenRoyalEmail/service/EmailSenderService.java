package com.samreact.GoldenRoyalEmail.service;

import jakarta.mail.MessagingException;

public interface EmailSenderService {
    void sendContactHtml(String name, String fromEmail, String messageBody) throws MessagingException;
}
